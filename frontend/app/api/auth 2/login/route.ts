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
    session.user = {
        id: persona.id,
        nombre: persona.nombre,
        primerApellido: persona.primerApellido,
        segundoApellido: persona.segundoApellido,
        // Backend doesn't return roles in Persona directly yet, 
        // but we can assume basic rights or fetch them if needed.
        // For now, let's treat everyone as authenticated.
        roles: ["user"] 
    };
    session.isLoggedIn = true;
    
    await session.save();

    return NextResponse.json(session.user);

  } catch (error) {
    console.error("Login error:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}
