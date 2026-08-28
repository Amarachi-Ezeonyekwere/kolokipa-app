"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from "@/components/ui/dialog";
import { api, type Member } from "@/lib/api";

export function CreateCycleDialog({
  circleId,
  members,
}: {
  circleId: string;
  members: Member[];
}) {
  const router = useRouter();
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(formData: FormData) {
    setLoading(true);
    setError(null);
    try {
      await api.createCycle(circleId, {
        cycleNumber: Number(formData.get("cycleNumber")),
        collectorMemberId: formData.get("collectorMemberId") as string,
      });
      setOpen(false);
      router.refresh();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Something went wrong");
    } finally {
      setLoading(false);
    }
  }

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button disabled={members.length === 0}>Start a Cycle</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className="font-display">Start a new cycle</DialogTitle>
        </DialogHeader>

        <form action={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="cycleNumber">Cycle number</Label>
            <input
              id="cycleNumber"
              name="cycleNumber"
              type="number"
              min="1"
              required
              className="w-full h-9 rounded-md border border-input bg-transparent px-3 text-sm"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="collectorMemberId">Collector</Label>
            <select
              id="collectorMemberId"
              name="collectorMemberId"
              required
              className="w-full h-9 rounded-md border border-input bg-transparent px-3 text-sm"
            >
              {members.map((m) => (
                <option key={m.id} value={m.id}>
                  {m.fullName}
                </option>
              ))}
            </select>
          </div>

          {error && <p className="text-sm text-destructive">{error}</p>}

          <DialogFooter>
            <Button type="submit" disabled={loading}>
              {loading ? "Creating..." : "Create cycle"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}