import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function GET(request: NextRequest) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn || !session.user || !session.user.roles.includes("admin")) { // Basic role check
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const serviceUrl = process.env.APPOINTMENT_SERVICE_URL || "http://appointment-service:8080";

    try {
        // Admin gets all appointments
        const res = await fetch(`${serviceUrl}/appointments`, {
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
