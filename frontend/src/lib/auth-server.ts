import "server-only";
import { cookies } from "next/headers";
import { TOKEN_COOKIE_NAME } from "./auth";

export async function getServerToken(): Promise<string | null> {
  const cookieStore = await cookies();
  return cookieStore.get(TOKEN_COOKIE_NAME)?.value ?? null;
}