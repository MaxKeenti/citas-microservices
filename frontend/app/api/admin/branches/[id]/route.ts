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
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    const res = await fetch(`${hresourcesUrl}/branches/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });
    
     if (!res.ok) {
        return NextResponse.json({ message: `Backend error: ${res.status}` }, { status: res.status });
    }
    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    return NextResponse.json({ message: "Error updating branch" }, { status: 500 });
  }
}

export async function DELETE(request: Request, { params }: { params: Promise<{ id: string }> }) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const { id } = await params;
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    const res = await fetch(`${hresourcesUrl}/branches/${id}`, {
        method: "DELETE"
    });
    
     if (!res.ok) {
        return NextResponse.json({ message: `Backend error: ${res.status}` }, { status: res.status });
    }
    return NextResponse.json({ message: "Deleted" });
  } catch (error) {
    return NextResponse.json({ message: "Error deleting branch" }, { status: 500 });
  }
}
