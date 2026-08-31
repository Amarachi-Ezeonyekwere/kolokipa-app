"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { api, type Contribution } from "@/lib/api";

const CURRENCY_SYMBOLS: Record<string, string> = {
  NGN: "₦",
  GHS: "₵",
  KES: "KSh",
  ZAR: "R",
  XOF: "CFA",
  ETB: "Br",
};

export function ContributionList({
  circleId,
  cycleId,
  contributions,
  currency,
}: {
  circleId: string;
  cycleId: string;
  contributions: Contribution[];
  currency: string;
}) {
  const router = useRouter();
  const [payingId, setPayingId] = useState<string | null>(null);

  async function handleMarkPaid(contributionId: string) {
    setPayingId(contributionId);
    try {
      await api.markAsPaid(circleId, cycleId, contributionId);
      router.refresh();
    } catch (err) {
      console.error("Failed to mark as paid:", err);
    } finally {
      setPayingId(null);
    }
  }

  const symbol = CURRENCY_SYMBOLS[currency] ?? currency;

  return (
    <div className="space-y-2">
      {contributions.map((c) => (
        <div
          key={c.id}
          className="flex items-center justify-between rounded-md border border-border px-4 py-3"
        >
          <div>
            <p className="font-medium">{c.memberName}</p>
            <p className="text-sm text-muted-foreground">
              {symbol}
              {c.amount.toLocaleString()}
            </p>
          </div>

          {c.status === "PAID" ? (
            <Badge variant="secondary">PAID</Badge>
          ) : (
            <Button
              size="sm"
              variant="outline"
              disabled={payingId === c.id}
              onClick={() => handleMarkPaid(c.id)}
            >
              {payingId === c.id ? "Marking..." : "Mark as Paid"}
            </Button>
          )}
        </div>
      ))}
    </div>
  );
}