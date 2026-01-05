
import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";

export async function POST(request: NextRequest) {
  const session = await getIronSession<SessionData>(request, NextResponse.next(), sessionOptions);
  session.destroy();
  
  // Use Host header to fix Docker internal hostname issue
  const host = request.headers.get("host") || "localhost:3000";
  const protocol = process.env.NODE_ENV === "production" ? "https" : "http";
  const url = new URL("/login", `${protocol}://${host}`);
  
  const res = NextResponse.redirect(url);
  return res;
}
