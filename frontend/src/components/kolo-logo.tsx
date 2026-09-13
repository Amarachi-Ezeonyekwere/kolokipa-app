export function KoloLogo({ size = 32 }: { size?: number }) {
  return (
    <svg viewBox="0 0 100 100" width={size} height={size} aria-hidden="true">
      <circle cx="50" cy="50" r="45" fill="none" stroke="#C9A227" strokeWidth="3" />
      <circle cx="50" cy="14" r="7" fill="#C9A227" />
      <circle cx="79" cy="32" r="5" fill="#0B5D3B" />
      <circle cx="79" cy="68" r="5" fill="#0B5D3B" />
      <circle cx="21" cy="68" r="5" fill="#0B5D3B" />
      <circle cx="21" cy="32" r="5" fill="#0B5D3B" />
    </svg>
  );
}