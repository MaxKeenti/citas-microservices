import { NextResponse } from "next/server";
import { getIronSession } from "iron-session";
import { defaultSession, sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";

export async function GET(request: Request, { params }: { params: Promise<{ id: string }> }) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const { id } = await params;
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    const res = await fetch(`${hresourcesUrl}/employees/${id}/horarios`);
    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    console.error(error);
    return NextResponse.json({ message: "Error fetching employee schedules" }, { status: 500 });
  }
}

export async function POST(request: Request, { params }: { params: Promise<{ id: string }> }) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const { id } = await params;
    const body = await request.json(); // Expects { idHorario: ... }
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    
    const res = await fetch(`${hresourcesUrl}/employees/${id}/horarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    });

    if (!res.ok) {
        if (res.status === 409) return NextResponse.json({ message: "Schedule already assigned" }, { status: 409 });
        return NextResponse.json({ message: "Error assigning schedule" }, { status: res.status });
    }

    return NextResponse.json({ message: "Assigned" }, { status: 201 });
  } catch (error) {
    return NextResponse.json({ message: "Error assigning schedule" }, { status: 500 });
  }
}

export async function DELETE(request: Request, { params }: { params: Promise<{ id: string }> }) {
   // Wait, DELETE usually needs the scheduleId in the wrapper URL or as query param.
   // Or we can use `[id]/schedules/[scheduleId]`.
   // But my file structure is `[id]/schedules/route.ts`? No, this file is `route.ts`.
   // So `DELETE` here would apply to... ?
   // Usually DELETE requests act on a resource identified by URL.
   // If I want to delete a specific schedule from an employee, I should probably do `DELETE /api/admin/employees/[id]/schedules/[scheduleId]`.
   
   // Let's create `[scheduleId]/route.ts` inside a subdirectory instead.
   return NextResponse.json({ message: "Method not allowed on collection" }, { status: 405 });
}
