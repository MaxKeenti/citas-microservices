import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";
import { cookies } from "next/headers";

export async function PUT(request: NextRequest) {
  try {
    const body = await request.json();
    const { id, nombre, primerApellido, segundoApellido, fechaNacimiento, idGenero } = body;

    const cookieStore = await cookies();
    const session = await getIronSession<SessionData>(cookieStore, sessionOptions);

    if (!session.isLoggedIn || !session.user) {
        return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
    }

    // Call Access Control Service
    const serviceUrl = process.env.ACCESS_CONTROL_SERVICE_URL;
    if (!serviceUrl) {
      throw new Error("ACCESS_CONTROL_SERVICE_URL is not defined");
    }

    const res = await fetch(`${serviceUrl}/auth/profile`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: session.user.id, // Ensure we update the logged in user
        nombre,
        primerApellido,
        segundoApellido,
        fechaNacimiento,
        idGenero: parseInt(idGenero),
      }),
    });

    if (!res.ok) {
      const errorText = await res.text();
      return NextResponse.json({ message: errorText || "Update failed" }, { status: res.status });
    }

    const updatedPersona = await res.json();

    // Update Session
    session.user = {
        ...session.user,
        nombre: updatedPersona.nombre,
        primerApellido: updatedPersona.primerApellido,
        segundoApellido: updatedPersona.segundoApellido,
    };
    await session.save();

    return NextResponse.json(session.user);

  } catch (error) {
    console.error("Profile update error:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}
