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
    const catalogUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";
    const res = await fetch(`${catalogUrl}/services`);
    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    return NextResponse.json({ message: "Error fetching services" }, { status: 500 });
  }
}

export async function POST(request: Request) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const body = await request.json();
    const catalogUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";
    
    // Default active to true if not specified
    if (body.activo === undefined) body.activo = true;

    const res = await fetch(`${catalogUrl}/services`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });

    if (!res.ok) {
        return NextResponse.json({ message: "Error creating service" }, { status: res.status });
    }

    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    return NextResponse.json({ message: "Error creating service" }, { status: 500 });
  }
}
