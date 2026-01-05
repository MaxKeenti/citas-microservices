import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function PUT(
    request: NextRequest,
    { params }: { params: Promise<{ id: string }> }
) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn || !session.user) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const hasAdminRole = session.user.roles.includes("admin");
    const hasEmployeeRole = session.user.roles.includes("employee");

    if (!hasAdminRole && !hasEmployeeRole) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const { id } = await params;
    const serviceUrl = process.env.APPOINTMENT_SERVICE_URL || "http://appointment-service:8080";

    try {
        const body = await request.json();
        const res = await fetch(`${serviceUrl}/appointments/${id}/status`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body),
        });

        if (!res.ok) {
            return NextResponse.json({ message: await res.text() }, { status: res.status });
        }

        const data = await res.json();
        return NextResponse.json(data);
    } catch (error) {
        console.error("Error updating appointment status:", error);
        return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
    }
}
