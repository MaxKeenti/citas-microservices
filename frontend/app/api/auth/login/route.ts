import { getIronSession } from "iron-session";
import { defaultSession, sessionOptions, SessionData } from "@/lib/session";
import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function POST(request: Request) {
  const { username, password } = await request.json();

  try {
    const backendUrl = process.env.ACCESS_CONTROL_SERVICE_URL || "http://access-control-service:8080";
    
    // Call Access Control Service
    // Note: In real production, this should be https
    const response = await fetch(`${backendUrl}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username,
        password,
      }),
    });

    if (!response.ok) {
        return NextResponse.json({ message: "Invalid credentials" }, { status: 401 });
    }

    const persona = await response.json();

    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
    
    // Map Persona to User session
    // Map Backend DTO to Frontend Session
    session.user = {
        id: persona.id,
        nombre: persona.nombre,
        primerApellido: persona.primerApellido,
        segundoApellido: persona.segundoApellido,
        fechaNacimiento: persona.fechaNacimiento,
        idGenero: persona.idGenero,
        roles: persona.roles || ["user"] // Use roles from backend or default
    };
    session.isLoggedIn = true;
    
    await session.save();

    return NextResponse.json(session.user);

  } catch (error) {
    console.error("Login error:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}
