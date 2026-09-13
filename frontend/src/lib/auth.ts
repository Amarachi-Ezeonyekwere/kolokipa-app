const TOKEN_COOKIE = "kolokipa_token";

export function setClientToken(token: string) {
  document.cookie = `${TOKEN_COOKIE}=${token}; path=/; max-age=${60 * 60 * 24}; SameSite=Lax`;
}

export function getClientToken(): string | null {
  const match = document.cookie.match(new RegExp(`(?:^|; )${TOKEN_COOKIE}=([^;]*)`));
  return match ? decodeURIComponent(match[1]) : null;
}

export function clearClientToken() {
  document.cookie = `${TOKEN_COOKIE}=; path=/; max-age=0`;
}

export const TOKEN_COOKIE_NAME = TOKEN_COOKIE;