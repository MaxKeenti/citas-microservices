import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    const { username, password, confirmPassword, nombre, primerApellido, segundoApellido, fechaNacimiento, idGenero } = body;

    // Call Access Control Service
    const serviceUrl = process.env.ACCESS_CONTROL_SERVICE_URL;
    if (!serviceUrl) {
      throw new Error("ACCESS_CONTROL_SERVICE_URL is not defined");
    }

    const res = await fetch(`${serviceUrl}/auth/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username,
        password,
        confirmPassword,
        nombre,
        primerApellido,
        segundoApellido,
        fechaNacimiento,
        idGenero: parseInt(idGenero),
      }),
    });

    if (!res.ok) {
      const errorText = await res.text();
      return NextResponse.json({ message: errorText || "Registration failed" }, { status: res.status });
    }

    const newUser = await res.json();

    // Automatically log in the user by creating a session?
    // Let's forward them to login page for simplicity first, or auto-login.
    // Auto-login is better UX.
    
    // Call login (or simulate it since we have the user object, but we need the Role ID which comes from Usuario, 
    // and the Create endpoint returned Persona. 
    // The previous legacy controller returned a Persona.
    // The backend `login` endpoint also returns Persona.
    // But `login` endpoint checks credentials.
    // We already know credentials are valid.
    // However, our Session structure needs `usuario` info (like role). 
    // The `register` endpoint returns `Persona` but it might NOT have the `usuario` relation loaded eagerly?
    // Let's just create session manually or re-login. Re-login via internal fetch is safest.
    
    // Internal Login Call
    const loginRes = await fetch(`${serviceUrl}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    if (!loginRes.ok) {
        return NextResponse.json({ message: "Registration successful but auto-login failed. Please login manually." }, { status: 200 }); // Status 200 to indicate success of registration
    }

    const userData = await loginRes.json();
    
    const session = await getIronSession<SessionData>(request, NextResponse.next(), sessionOptions);
    session.user = {
      ...userData,
      isLoggedIn: true,
    };
    await session.save();

    return NextResponse.json(userData);

  } catch (error) {
    console.error("Registration error:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}
