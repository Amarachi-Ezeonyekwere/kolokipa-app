"use client";

type KoloRingMember = {
  id: string;
  fullName: string;
  isCollector?: boolean;
};

export function KoloRing({
  members,
  size = 220,
}: {
  members: KoloRingMember[];
  size?: number;
}) {
  const center = size / 2;
  const radius = size / 2 - 28;

  return (
    <div className="relative" style={{ width: size, height: size }}>
      <svg width={size} height={size} className="absolute inset-0">
        <circle
          cx={center}
          cy={center}
          r={radius}
          fill="none"
          stroke="var(--border)"
          strokeWidth={2}
          strokeDasharray="4 6"
        />
        {members.map((member, i) => {
          const angle = (i / Math.max(members.length, 1)) * 2 * Math.PI - Math.PI / 2;
          const x = center + radius * Math.cos(angle);
          const y = center + radius * Math.sin(angle);

          return (
            <g key={member.id}>
              <circle
                cx={x}
                cy={y}
                r={member.isCollector ? 14 : 10}
                fill={member.isCollector ? "var(--accent)" : "var(--primary)"}
                stroke="var(--surface)"
                strokeWidth={3}
              />
            </g>
          );
        })}
      </svg>

      <div className="absolute inset-0 flex flex-col items-center justify-center text-center px-8">
        <span className="text-sm text-muted-foreground">Members</span>
        <span className="font-display text-3xl">{members.length}</span>
      </div>
    </div>
  );
}