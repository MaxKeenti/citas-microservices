
import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";

export async function POST(request: NextRequest) {
  const session = await getIronSession<SessionData>(request, NextResponse.next(), sessionOptions);
  session.destroy();
  const res = NextResponse.redirect(new URL("/login", request.url));
  return res;
}
