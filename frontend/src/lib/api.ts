const API_URL =
  typeof window === "undefined"
    ? process.env.INTERNAL_API_URL ?? "http://localhost:8080"
    : process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

export type Circle = {
  id: string;
  name: string;
  contributionAmount: number;
  cycleFrequency: string;
  terminologyProfile: string;
  currency: string;
  createdAt: string;
};

export type Member = {
  id: string;
  circleId: string;
  fullName: string;
  email: string;
  payoutPosition: number | null;
  joinedAt: string;
};

export type Cycle = {
  id: string;
  circleId: string;
  cycleNumber: number;
  collectorMemberId: string;
  collectorName: string;
  status: string;
  startDate: string;
  endDate: string | null;
};

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`${API_URL}${path}`, {
    ...options,
    headers: { "Content-Type": "application/json", ...options?.headers },
    cache: "no-store",
  });

  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new Error(body.message ?? `Request failed: ${res.status}`);
  }

  return res.json();
}

export const api = {
  getCircles: () => request<Circle[]>("/circles"),
  getCircle: (id: string) => request<Circle>(`/circles/${id}`),
  createCircle: (data: {
    name: string;
    contributionAmount: number;
    cycleFrequency: string;
    terminologyProfile: string;
    currency: string;
  }) => request<Circle>("/circles", { method: "POST", body: JSON.stringify(data) }),
  getMembers: (circleId: string) => request<Member[]>(`/circles/${circleId}/members`),
  addMember: (circleId: string, data: { fullName: string; email: string }) =>
    request<Member>(`/circles/${circleId}/members`, {
      method: "POST",
      body: JSON.stringify(data),
    }),
  getCycles: (circleId: string) => request<Cycle[]>(`/circles/${circleId}/cycles`),
  createCycle: (circleId: string, data: { cycleNumber: number; collectorMemberId: string }) =>
    request<Cycle>(`/circles/${circleId}/cycles`, {
      method: "POST",
      body: JSON.stringify(data),
    }),
};