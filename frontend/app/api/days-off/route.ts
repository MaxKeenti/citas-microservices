import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function POST(request: NextRequest) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn || !session.user || !session.user.roles.includes("admin")) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const serviceUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";

    try {
        const body = await request.json();
        const res = await fetch(`${serviceUrl}/days-off`, {
            method: "POST",
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
        console.error("Error creating days off:", error);
        return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
    }
}
