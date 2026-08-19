import { Button } from "@/components/ui/button"

export default function Home() {
  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-background text-foreground">
      <h1 className="text-3xl font-semibold mb-6">KoloKipa</h1>
      <Button>Join a Circle</Button>
    </div>
  )
}