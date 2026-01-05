import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function DELETE(
    request: NextRequest,
    { params }: { params: Promise<{ id: string }> }
) {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn || !session.user || !session.user.roles.includes("admin")) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const { id } = await params;
    const serviceUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";

    try {
        const res = await fetch(`${serviceUrl}/days-off/${id}`, {
            method: "DELETE",
        });

        if (!res.ok) {
            return NextResponse.json({ message: await res.text() }, { status: res.status });
        }

        return new NextResponse(null, { status: 204 });
    } catch (error) {
        console.error("Error deleting day off:", error);
        return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
    }
}
