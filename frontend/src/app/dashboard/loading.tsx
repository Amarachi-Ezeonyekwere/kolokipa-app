export default function DashboardLoading() {
  return (
    <main className="min-h-screen bg-background px-6 py-16 sm:px-12">
      <div className="mx-auto max-w-5xl">
        <div className="flex items-center justify-between mb-12">
          <div>
            <div className="h-9 w-40 bg-muted rounded animate-pulse mb-2" />
            <div className="h-4 w-64 bg-muted rounded animate-pulse" />
          </div>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {[1, 2, 3].map((i) => (
            <div key={i} className="h-40 rounded-lg border border-border p-6">
              <div className="h-5 w-32 bg-muted rounded animate-pulse mb-4" />
              <div className="h-8 w-24 bg-muted rounded animate-pulse" />
            </div>
          ))}
        </div>
      </div>
    </main>
  );
}