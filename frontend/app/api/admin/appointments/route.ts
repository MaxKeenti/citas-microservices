import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function GET(request: NextRequest) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn || !session.user) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const hasAdminRole = session.user.roles.includes("admin");
    const hasEmployeeRole = session.user.roles.includes("employee");

    if (!hasAdminRole && !hasEmployeeRole) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const serviceUrl = process.env.APPOINTMENT_SERVICE_URL || "http://appointment-service:8080";
    let url = `${serviceUrl}/appointments`;

    // If employee but not admin, filter by their ID
    if (hasEmployeeRole && !hasAdminRole) {
        url += `?empleadoId=${session.user.id}`;
    }

    try {
        const res = await fetch(url, {
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!res.ok) {
            return NextResponse.json({ message: await res.text() }, { status: res.status });
        }

        const data = await res.json();
        return NextResponse.json(data);
    } catch (error) {
        console.error("Error fetching admin appointments:", error);
        return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
    }
}
