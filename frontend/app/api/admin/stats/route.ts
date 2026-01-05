import { NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function GET() {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);

    if (!session.isLoggedIn || !session.user || !session.user.roles.includes("admin")) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    const appointmentUrl = process.env.APPOINTMENT_SERVICE_URL || "http://appointment-service:8080";
    const accessControlUrl = process.env.ACCESS_CONTROL_SERVICE_URL || "http://access-control-service:8080";

    try {
        const [apptRes, userRes] = await Promise.all([
            fetch(`${appointmentUrl}/stats`),
            fetch(`${accessControlUrl}/stats`)
        ]);

        const apptStats = apptRes.ok ? await apptRes.json() : {};
        const userStats = userRes.ok ? await userRes.json() : {};

        return NextResponse.json({
            ...apptStats,
            ...userStats
        });

    } catch (error) {
        console.error("Error fetching stats:", error);
        return NextResponse.json({
            totalAppointments: 0,
            appointmentsToday: 0,
            pendingConfirmations: 0,
            activeUsers: 0
        });
    }
}
