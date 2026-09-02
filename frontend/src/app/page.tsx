import { api } from "@/lib/api";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import Link from "next/link";
import { CreateCircleDialog } from "@/components/create-circle-dialog";


const CURRENCY_SYMBOLS: Record<string, string> = {
  NGN: "₦",
  GHS: "₵",
  KES: "KSh",
  ZAR: "R",
  XOF: "CFA",
  ETB: "Br",
};

export default async function Home() {
  const circles = await api.getCircles().catch(() => []);

  return (
    <main className="min-h-screen bg-background px-6 py-16 sm:px-12">
      <div className="mx-auto max-w-5xl">
        <div className="flex items-center justify-between mb-12">
          <div>
            <h1 className="text-4xl font-display">KoloKipa</h1>
            <p className="text-muted-foreground mt-1">
              Traditional savings. Modern accountability.
            </p>
          </div>
          <CreateCircleDialog />
        </div>

        {circles.length === 0 ? (
          <div className="rounded-lg border border-dashed border-border py-24 text-center">
            <p className="text-lg font-display mb-2">No circles yet</p>
            <p className="text-muted-foreground">
              Start your first savings circle to begin tracking contributions.
            </p>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {circles.map((circle) => (
              <Link key={circle.id} href={`/circles/${circle.id}`}>
                <Card className="hover:shadow-lg transition-shadow cursor-pointer h-full">
                  <CardHeader>
                    <CardTitle className="font-display">{circle.name}</CardTitle>
                  </CardHeader>
                  <CardContent className="space-y-3">
                    <p className="text-2xl font-display text-primary">
                      {CURRENCY_SYMBOLS[circle.currency] ?? circle.currency}
                      {circle.contributionAmount.toLocaleString()}
                    </p>
                    <div className="flex gap-2">
                      <Badge variant="secondary">{circle.cycleFrequency}</Badge>
                      <Badge variant="outline">{circle.terminologyProfile}</Badge>
                    </div>
                  </CardContent>
                </Card>
              </Link>
            ))}
          </div>
        )}
      </div>
    </main>
  );
}