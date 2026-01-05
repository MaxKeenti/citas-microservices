import { NextResponse } from "next/server";
import { getIronSession } from "iron-session";
import { defaultSession, sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";

export async function PUT(request: Request, { params }: { params: Promise<{ id: string }> }) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const { id } = await params;
    const body = await request.json();
    const catalogUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";
    
    const res = await fetch(`${catalogUrl}/services/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });

    if (!res.ok) {
        return NextResponse.json({ message: "Error updating service" }, { status: res.status });
    }

    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    return NextResponse.json({ message: "Error updating service" }, { status: 500 });
  }
}

export async function DELETE(request: Request, { params }: { params: Promise<{ id: string }> }) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const { id } = await params;
    const catalogUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";
    
    const res = await fetch(`${catalogUrl}/services/${id}`, {
      method: "DELETE",
    });

    if (!res.ok) {
        return NextResponse.json({ message: "Error deleting service" }, { status: res.status });
    }

    return NextResponse.json({ message: "Service deleted" });
  } catch (error) {
    return NextResponse.json({ message: "Error deleting service" }, { status: 500 });
  }
}
