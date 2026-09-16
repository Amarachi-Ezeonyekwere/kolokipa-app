export default function CircleDetailLoading() {
  return (
    <main className="min-h-screen bg-background px-6 py-16 sm:px-12">
      <div className="mx-auto max-w-4xl">
        <div className="h-10 w-64 bg-muted rounded animate-pulse mb-4" />
        <div className="h-6 w-40 bg-muted rounded animate-pulse mb-8" />
        <div className="h-32 rounded-lg border border-border animate-pulse mb-10" />
        <div className="h-64 w-64 rounded-full border border-border animate-pulse mx-auto mb-12" />
      </div>
    </main>
  );
}