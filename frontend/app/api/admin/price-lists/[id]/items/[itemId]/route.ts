import { NextResponse } from "next/server";
import { getIronSession } from "iron-session";
import { defaultSession, sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";

const catalogUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";

export async function DELETE(request: Request, { params }: { params: Promise<{ id: string, itemId: string }> }) {
    // Note: params might not flatten nested routes correctly in standard Next.js without folder struct.
    // But [id]/items/[itemId]/route.ts expects params object.

    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
    if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    try {
        const { id, itemId } = await params;
        const res = await fetch(`${catalogUrl}/price-lists/${id}/items/${itemId}`, {
            method: "DELETE"
        });

        if (!res.ok) throw new Error("Failed");
        return NextResponse.json({ message: "Deleted" });
    } catch (error) {
        return NextResponse.json({ message: "Error deleting item" }, { status: 500 });
    }
}
