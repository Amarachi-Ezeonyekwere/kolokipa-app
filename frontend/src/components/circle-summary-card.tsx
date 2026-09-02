import { Card, CardContent } from "@/components/ui/card";
import type { CircleSummary } from "@/lib/api";

const CURRENCY_SYMBOLS: Record<string, string> = {
  NGN: "₦", GHS: "₵", KES: "KSh", ZAR: "R", XOF: "CFA", ETB: "Br",
};

export function CircleSummaryCard({
  summary,
  currency,
}: {
  summary: CircleSummary;
  currency: string;
}) {
  const symbol = CURRENCY_SYMBOLS[currency] ?? currency;

  return (
    <Card className="mb-10">
      <CardContent className="grid grid-cols-2 sm:grid-cols-4 gap-6 py-6">
        <div>
          <p className="text-sm text-muted-foreground">Group Health</p>
          <p className="text-2xl font-display text-primary">
            {summary.completionRatePercent.toFixed(0)}%
          </p>
        </div>
        <div>
          <p className="text-sm text-muted-foreground">Collected</p>
          <p className="text-2xl font-display">
            {symbol}{summary.totalCollected.toLocaleString()}
          </p>
        </div>
        <div>
          <p className="text-sm text-muted-foreground">Cycles Completed</p>
          <p className="text-2xl font-display">{summary.completedCycles}</p>
        </div>
        <div>
          <p className="text-sm text-muted-foreground">Members</p>
          <p className="text-2xl font-display">{summary.totalMembers}</p>
        </div>
      </CardContent>
    </Card>
  );
}