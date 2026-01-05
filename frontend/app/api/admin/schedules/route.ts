import { NextResponse } from "next/server";
import { getIronSession } from "iron-session";
import { defaultSession, sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";

export async function GET() {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    const res = await fetch(`${hresourcesUrl}/schedules`);
    
    if (!res.ok) {
        const text = await res.text();
        console.error("Backend Error:", res.status, text);
        return NextResponse.json({ message: `Backend error: ${res.status}` }, { status: res.status });
    }

    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    console.error("Fetch Error:", error);
    return NextResponse.json({ message: "Error fetching schedules" }, { status: 500 });
  }
}
