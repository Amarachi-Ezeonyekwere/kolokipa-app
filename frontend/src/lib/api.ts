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

export type AuthResponse = {
  userId: string;
  fullName: string;
  email: string;
  token: string;
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

async function request<T>(path: string, options?: RequestInit & { token?: string | null }): Promise<T> {
  const { token, ...fetchOptions } = options ?? {};

  const res = await fetch(`${API_URL}${path}`, {
    ...fetchOptions,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...fetchOptions?.headers,
    },
    cache: "no-store",
  });


  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new Error(body.message ?? `Request failed: ${res.status}`);
  }

  return res.json();
}

export const api = {
  getCircles: (token?: string | null) => request<Circle[]>("/circles", { token }),
  getCircle: (id: string, token?: string | null) => request<Circle>(`/circles/${id}`, { token }),
  createCircle: (data: {
    name: string;
    contributionAmount: number;
    cycleFrequency: string;
    terminologyProfile: string;
    currency: string;
    timezone: string;
  }, token?: string | null) =>
    request<Circle>("/circles", { method: "POST", body: JSON.stringify(data), token }),
  getMembers: (circleId: string, token?: string | null) =>
    request<Member[]>(`/circles/${circleId}/members`, { token }),
  addMember: (circleId: string, data: { fullName: string; email: string }, token?: string | null) =>
    request<Member>(`/circles/${circleId}/members`, {
      method: "POST",
      body: JSON.stringify(data),
      token,
    }),
  getCycles: (circleId: string, token?: string | null) =>
    request<Cycle[]>(`/circles/${circleId}/cycles`, { token }),
  createCycle: (circleId: string, token?: string | null) =>
    request<Cycle>(`/circles/${circleId}/cycles`, { method: "POST", token }),
  getContributions: (circleId: string, cycleId: string, token?: string | null) =>
    request<Contribution[]>(`/circles/${circleId}/cycles/${cycleId}/contributions`, { token }),
  markAsPaid: (circleId: string, cycleId: string, contributionId: string, token?: string | null) =>
    request<Contribution>(
      `/circles/${circleId}/cycles/${cycleId}/contributions/${contributionId}/pay`,
      { method: "PATCH", token }
    ),
  getSummary: (circleId: string, token?: string | null) =>
    request<CircleSummary>(`/circles/${circleId}/summary`, { token }),
  getMemberHistory: (circleId: string, memberId: string, token?: string | null) =>
    request<MemberContribution[]>(`/circles/${circleId}/members/${memberId}/contributions`, { token }),
  getMissedPayments: (circleId: string, token?: string | null) =>
    request<MissedPayment[]>(`/circles/${circleId}/missed-payments`, { token }),
  register: (data: { fullName: string; email: string; password: string }) =>
    request<AuthResponse>("/auth/register", { method: "POST", body: JSON.stringify(data) }),
  login: (data: { email: string; password: string }) =>
    request<AuthResponse>("/auth/login", { method: "POST", body: JSON.stringify(data) }),
};