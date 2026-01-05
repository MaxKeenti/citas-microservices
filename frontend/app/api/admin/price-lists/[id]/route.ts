import { NextResponse } from "next/server";
import { getIronSession } from "iron-session";
import { defaultSession, sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";

const catalogUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";

export async function PUT(request: Request, { params }: { params: Promise<{ id: string }> }) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
    if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    try {
        const { id } = await params;
        const body = await request.json();

        // Check if we are updating items (special flag in body?) or the list info
        // Actually, usually separate route for items.
        // This is for updating list metadata (name, active, dates).

        const res = await fetch(`${catalogUrl}/price-lists/${id}`, {
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
        return NextResponse.json({ message: "Error updating price list" }, { status: 500 });
    }
}

export async function DELETE(request: Request, { params }: { params: Promise<{ id: string }> }) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
    if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    try {
        const { id } = await params;
        const res = await fetch(`${catalogUrl}/price-lists/${id}`, {
            method: "DELETE"
        });

        if (!res.ok) {
            return NextResponse.json({ message: `Backend error: ${res.status}` }, { status: res.status });
        }
        return NextResponse.json({ message: "Deleted" });
    } catch (error) {
        return NextResponse.json({ message: "Error deleting price list" }, { status: 500 });
    }
}
