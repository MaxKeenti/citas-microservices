import { NextRequest, NextResponse } from "next/server";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";
import { sessionOptions, SessionData } from "@/lib/session";

export async function GET(request: NextRequest) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  
  // Optional: protect if needed, but availability check might be public
  // if (!session.isLoggedIn) {
  //   return NextResponse.json({ error: "Unauthorized" }, { status: 401 });
  // }

  const searchParams = request.nextUrl.searchParams;
  const employeeId = searchParams.get("employeeId");
  const date = searchParams.get("date");
  const serviceDuration = searchParams.get("serviceDuration");

  if (!employeeId || !date || !serviceDuration) {
      return NextResponse.json({ error: "Missing parameters" }, { status: 400 });
  }

  try {
    const res = await fetch(`${process.env.APPOINTMENT_SERVICE_URL}/availability?employeeId=${employeeId}&date=${date}&serviceDuration=${serviceDuration}`);
    if (!res.ok) {
        throw new Error("Failed to fetch availability");
    }
    const slots = await res.json();
    return NextResponse.json(slots);
  } catch (error) {
    console.error("Availability fetch error:", error);
    return NextResponse.json({ error: "Internal Server Error" }, { status: 500 });
  }
}
