
import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";

export async function POST(request: NextRequest) {
  const session = await getIronSession<SessionData>(request, NextResponse.next(), sessionOptions);
  session.destroy();
  
  // Use Host header to default to correct browser-facing URL
  const host = request.headers.get("host") || "localhost:3000";
  return NextResponse.redirect(new URL("/login", `http://${host}`));
}
