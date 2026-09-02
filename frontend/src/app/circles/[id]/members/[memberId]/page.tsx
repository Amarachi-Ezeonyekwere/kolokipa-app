import { api } from "@/lib/api";
import { notFound } from "next/navigation";
import { Badge } from "@/components/ui/badge";

const CURRENCY_SYMBOLS: Record<string, string> = {
  NGN: "₦", GHS: "₵", KES: "KSh", ZAR: "R", XOF: "CFA", ETB: "Br",
};

export default async function MemberHistoryPage({
  params,
}: {
  params: Promise<{ id: string; memberId: string }>;
}) {
  const { id, memberId } = await params;

  const [circle, history] = await Promise.all([
    api.getCircle(id).catch(() => null),
    api.getMemberHistory(id, memberId).catch(() => []),
  ]);

  if (!circle) {
    notFound();
  }

  const symbol = CURRENCY_SYMBOLS[circle.currency] ?? circle.currency;

  return (
    <main className="min-h-screen bg-background px-6 py-16 sm:px-12">
      <div className="mx-auto max-w-2xl">
        <h1 className="text-3xl font-display mb-1">Contribution History</h1>
        <p className="text-muted-foreground mb-8">{circle.name}</p>

        {history.length === 0 ? (
          <p className="text-muted-foreground">No contribution history yet.</p>
        ) : (
          <div className="space-y-2">
            {history.map((h) => (
              <div
                key={h.contributionId}
                className="flex items-center justify-between rounded-md border border-border px-4 py-3"
              >
                <div>
                  <p className="font-medium">Cycle {h.cycleNumber}</p>
                  <p className="text-sm text-muted-foreground">
                    {symbol}{h.amount.toLocaleString()}
                    {h.paidAt && ` · paid ${new Date(h.paidAt).toLocaleDateString()}`}
                  </p>
                </div>
                <Badge
                  variant={
                    h.status === "PAID" ? "secondary" :
                    h.status === "MISSED" ? "destructive" : "outline"
                  }
                >
                  {h.status}
                </Badge>
              </div>
            ))}
          </div>
        )}
      </div>
    </main>
  );
}