"use client";

import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { clearClientToken } from "@/lib/auth";

export function LogoutButton() {
  const router = useRouter();

  function handleLogout() {
    clearClientToken();
    router.push("/");
    router.refresh();
  }

  return (
    <Button variant="outline" onClick={handleLogout}>
      Sign out
    </Button>
  );
}