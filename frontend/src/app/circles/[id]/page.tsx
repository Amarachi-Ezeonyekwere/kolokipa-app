import { api } from "@/lib/api";
import { notFound } from "next/navigation";
import { KoloRing } from "@/components/kolo-ring";
import { Badge } from "@/components/ui/badge";
import { StartCycleButton } from "@/components/start-cycle-button";
import { ContributionList } from "@/components/contribution-list";

const CURRENCY_SYMBOLS: Record<string, string> = {
  NGN: "₦",
  GHS: "₵",
  KES: "KSh",
  ZAR: "R",
  XOF: "CFA",
  ETB: "Br",
};

export default async function CircleDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;

  const [circle, members, cycles] = await Promise.all([
    api.getCircle(id).catch((err) => {
      console.error("Failed to load circle:", err);
      return null;
    }),
    api.getMembers(id).catch((err) => {
      console.error("Failed to load members:", err);
      return [];
    }),
    api.getCycles(id).catch((err) => {
      console.error("Failed to load cycles:", err);
      return [];
    }),
  ]);

  if (!circle) {
    notFound();
  }

  const cyclesWithContributions = await Promise.all(
    cycles.map(async (cycle) => ({
      cycle,
      contributions: await api.getContributions(id, cycle.id).catch(() => []),
    }))
  );

  const activeCollectorId = cycles.find((c) => c.status !== "COMPLETED")?.collectorMemberId;

  const ringMembers = members.map((m) => ({
    id: m.id,
    fullName: m.fullName,
    isCollector: m.id === activeCollectorId,
  }));

  return (
    <main className="min-h-screen bg-background px-6 py-16 sm:px-12">
      <div className="mx-auto max-w-4xl">
        <div className="mb-10">
          <h1 className="text-4xl font-display">{circle.name}</h1>
          <div className="flex gap-2 mt-3">
            <Badge variant="secondary">{circle.cycleFrequency}</Badge>
            <Badge variant="outline">{circle.terminologyProfile}</Badge>
          </div>
          <p className="text-2xl font-display text-primary mt-4">
            {CURRENCY_SYMBOLS[circle.currency] ?? circle.currency}
            {circle.contributionAmount.toLocaleString()}
          </p>
        </div>

        <div className="flex justify-center mb-12">
          <KoloRing members={ringMembers} />
        </div>

        <div className="flex items-center justify-between mb-4">
          <h2 className="text-2xl font-display">Cycles</h2>
          <StartCycleButton
           circleId={circle.id}
           disabled={cycles.length > 0 && cycles[cycles.length - 1].status !== "COMPLETED"}
          />
        </div>

        {cyclesWithContributions.length === 0 ? (
          <div className="rounded-lg border border-dashed border-border py-16 text-center">
            <p className="text-muted-foreground">No cycles yet. Start the first one.</p>
          </div>
        ) : (
          <div className="space-y-6">
            {cyclesWithContributions.map(({ cycle, contributions }) => (
              <div key={cycle.id} className="rounded-lg border border-border p-4">
                <div className="flex items-center justify-between mb-4">
                  <div>
                    <p className="font-medium">Cycle {cycle.cycleNumber}</p>
                    <p className="text-sm text-muted-foreground">
                      Collector: {cycle.collectorName}
                    </p>
                  </div>
                  <Badge variant={cycle.status === "UPCOMING" ? "secondary" : "outline"}>
                    {cycle.status}
                  </Badge>
                </div>

                <ContributionList
                  circleId={circle.id}
                  cycleId={cycle.id}
                  contributions={contributions}
                  currency={circle.currency}
                />
              </div>
            ))}
          </div>
        )}
      </div>
    </main>
  );
}