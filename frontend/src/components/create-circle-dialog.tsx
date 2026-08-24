"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from "@/components/ui/dialog";
import { api } from "@/lib/api";

const CIRCLE_OPTIONS = [
  { value: "esusu", label: "Esusu (Nigeria)", currency: "NGN", symbol: "₦" },
  { value: "susu", label: "Susu (Ghana)", currency: "GHS", symbol: "₵" },
  { value: "chama", label: "Chama (Kenya)", currency: "KES", symbol: "KSh" },
  { value: "stokvel", label: "Stokvel (South Africa)", currency: "ZAR", symbol: "R" },
  { value: "tontine", label: "Tontine (Senegal/Francophone West Africa)", currency: "XOF", symbol: "CFA" },
  { value: "equb", label: "Equb (Ethiopia)", currency: "ETB", symbol: "Br" },
];

export function CreateCircleDialog() {
  const router = useRouter();
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [selectedProfile, setSelectedProfile] = useState(CIRCLE_OPTIONS[0]);

  async function handleSubmit(formData: FormData) {
    setLoading(true);
    setError(null);
    try {
      await api.createCircle({
        name: formData.get("name") as string,
        contributionAmount: Number(formData.get("contributionAmount")),
        cycleFrequency: formData.get("cycleFrequency") as string,
        terminologyProfile: selectedProfile.value,
        currency: selectedProfile.currency,
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
        <Button>Start a Circle</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className="font-display">Start a new circle</DialogTitle>
        </DialogHeader>

        <form action={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="name">Circle name</Label>
            <Input id="name" name="name" placeholder="Women of Grace" required />
          </div>

          <div className="space-y-2">
            <Label htmlFor="contributionAmount">
              Contribution amount ({selectedProfile.symbol})
            </Label>
            <Input
              id="contributionAmount"
              name="contributionAmount"
              type="number"
              min="1"
              step="0.01"
              placeholder="20000"
              required
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="cycleFrequency">Cycle frequency</Label>
            <select
              id="cycleFrequency"
              name="cycleFrequency"
              required
              className="w-full h-9 rounded-md border border-input bg-transparent px-3 text-sm"
            >
              <option value="WEEKLY">Weekly</option>
              <option value="MONTHLY">Monthly</option>
            </select>
          </div>

          <div className="space-y-2">
            <Label htmlFor="terminologyProfile">What do you call it?</Label>
            <select
              id="terminologyProfile"
              name="terminologyProfile"
              required
              value={selectedProfile.value}
              onChange={(e) =>
                setSelectedProfile(
                  CIRCLE_OPTIONS.find((opt) => opt.value === e.target.value) ?? CIRCLE_OPTIONS[0]
                )
              }
              className="w-full h-9 rounded-md border border-input bg-transparent px-3 text-sm"
            >
              {CIRCLE_OPTIONS.map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>

          {error && <p className="text-sm text-destructive">{error}</p>}

          <DialogFooter>
            <Button type="submit" disabled={loading}>
              {loading ? "Creating..." : "Create circle"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}