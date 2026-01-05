import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function GET(
    request: NextRequest,
    { params }: { params: Promise<{ id: string }> }
) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn || !session.user) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const { id } = await params;
    const serviceUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";

    try {
        const res = await fetch(`${serviceUrl}/days-off/employee/${id}`, {
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
        console.error("Error fetching days off:", error);
        return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
    }
}
