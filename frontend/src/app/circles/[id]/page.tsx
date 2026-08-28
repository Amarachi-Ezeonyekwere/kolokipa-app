import { api } from "@/lib/api";
import { notFound } from "next/navigation";
import { KoloRing } from "@/components/kolo-ring";
import { Badge } from "@/components/ui/badge";
import { CreateCycleDialog } from "@/components/create-cycle-dialog";

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
          <CreateCycleDialog circleId={circle.id} members={members} />
        </div>

        {cycles.length === 0 ? (
          <div className="rounded-lg border border-dashed border-border py-16 text-center">
            <p className="text-muted-foreground">No cycles yet. Start the first one.</p>
          </div>
        ) : (
          <div className="space-y-3">
            {cycles.map((cycle) => (
              <div
                key={cycle.id}
                className="flex items-center justify-between rounded-lg border border-border p-4"
              >
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
            ))}
          </div>
        )}
      </div>
    </main>
  );
}