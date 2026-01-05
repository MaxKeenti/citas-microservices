import { NextResponse } from "next/server";
import { getIronSession } from "iron-session";
import { defaultSession, sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";

export async function DELETE(request: Request, { params }: { params: Promise<{ id: string, scheduleId: string }> }) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const { id, scheduleId } = await params;
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    
    // Call backend DELETE /employees/{id}/horarios/{idHorario}
    const res = await fetch(`${hresourcesUrl}/employees/${id}/horarios/${scheduleId}`, {
        method: "DELETE"
    });

    if (!res.ok) {
        return NextResponse.json({ message: "Error removing schedule" }, { status: res.status });
    }

    return NextResponse.json({ message: "Removed" });
  } catch (error) {
    return NextResponse.json({ message: "Error removing schedule" }, { status: 500 });
  }
}
