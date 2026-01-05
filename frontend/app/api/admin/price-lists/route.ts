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
        console.log("Fetching from:", `${catalogUrl}/price-lists`);
        const res = await fetch(`${catalogUrl}/price-lists`, { cache: "no-store" });
        if (!res.ok) {
            console.error("Backend Error fetch price lists:", res.status);
            return NextResponse.json({ message: `Backend error: ${res.status}` }, { status: res.status });
        }
        const data = await res.json();
        return NextResponse.json(data);
    } catch (error) {
        console.error(error);
        return NextResponse.json({ message: "Error fetching price lists" }, { status: 500 });
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
        // Check if we need to default 'estado' or 'activo'
        // Assuming backend handles defaults or we send basic info
        // For now, let's send what we have
        const res = await fetch(`${catalogUrl}/price-lists`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });

        if (!res.ok) {
            const text = await res.text();
            console.error("Backend Error create price list:", res.status, text);
            return NextResponse.json({ message: `Backend error: ${res.status}` }, { status: res.status });
        }
        const data = await res.json();
        return NextResponse.json(data);
    } catch (error) {
        console.error(error);
        return NextResponse.json({ message: "Error creating price list" }, { status: 500 });
    }
}
