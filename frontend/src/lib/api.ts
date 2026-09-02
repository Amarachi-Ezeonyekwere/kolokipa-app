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
  timezone: string;
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
  dueDate: string | null;
};

export type Contribution = {
  id: string;
  cycleId: string;
  memberId: string;
  memberName: string;
  amount: number;
  status: string;
  paidAt: string | null;
  createdAt: string;
};

export type CircleSummary = {
  circleId: string;
  totalMembers: number;
  completedCycles: number;
  upcomingCycles: number;
  totalCollected: number;
  totalExpected: number;
  completionRatePercent: number;
};

export type MemberContribution = {
  contributionId: string;
  cycleNumber: number;
  amount: number;
  status: string;
  paidAt: string | null;
};

export type MissedPayment = {
  contributionId: string;
  memberId: string;
  memberName: string;
  cycleNumber: number;
  amount: number;
  dueDate: string;
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
    timezone: string;
  }) => request<Circle>("/circles", { method: "POST", body: JSON.stringify(data) }),
  getMembers: (circleId: string) => request<Member[]>(`/circles/${circleId}/members`),
  addMember: (circleId: string, data: { fullName: string; email: string }) =>
    request<Member>(`/circles/${circleId}/members`, {
      method: "POST",
      body: JSON.stringify(data),
    }),
  getCycles: (circleId: string) => request<Cycle[]>(`/circles/${circleId}/cycles`),
  createCycle: (circleId: string) =>
  request<Cycle>(`/circles/${circleId}/cycles`, { method: "POST" }),

  getContributions: (circleId: string, cycleId: string) =>
    request<Contribution[]>(`/circles/${circleId}/cycles/${cycleId}/contributions`),
  markAsPaid: (circleId: string, cycleId: string, contributionId: string) =>
    request<Contribution>(
    `/circles/${circleId}/cycles/${cycleId}/contributions/${contributionId}/pay`,
    { method: "PATCH" }
  ),
  
  getSummary: (circleId: string) => request<CircleSummary>(`/circles/${circleId}/summary`),
  getMemberHistory: (circleId: string, memberId: string) =>
    request<MemberContribution[]>(`/circles/${circleId}/members/${memberId}/contributions`),


  getMissedPayments: (circleId: string) =>
    request<MissedPayment[]>(`/circles/${circleId}/missed-payments`),

};