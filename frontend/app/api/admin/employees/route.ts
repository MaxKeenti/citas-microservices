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
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    const res = await fetch(`${hresourcesUrl}/employees`);
    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    return NextResponse.json({ message: "Error fetching employees" }, { status: 500 });
  }
}

export async function POST(request: Request) {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  if (!session.isLoggedIn || !session.user?.roles.includes("admin")) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  try {
    const body = await request.json();
    
    // 1. Create User in Access Control
    const accessControlUrl = process.env.ACCESS_CONTROL_SERVICE_URL || "http://access-control-service:8080";
    const userRes = await fetch(`${accessControlUrl}/users`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            username: body.username || body.email, // Assume username provided
            password: body.password,
            nombre: body.nombre,
            primerApellido: body.primerApellido,
            segundoApellido: body.segundoApellido,
            fechaNacimiento: body.fechaNacimiento,
            idGenero: body.idGenero,
            idRol: 2 // Employee Role
        }),
    });

    if (!userRes.ok) {
        const err = await userRes.text();
        return NextResponse.json({ message: `Error creating user: ${err}` }, { status: userRes.status });
    }

    const createdUser = await userRes.json();
    const userId = createdUser.id;

    // 2. Create Employee in HResources
    const hresourcesUrl = process.env.HRESOURCES_SERVICE_URL || "http://hresources-service:8080";
    const empRes = await fetch(`${hresourcesUrl}/employees`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            id: userId,
            sucursal: { id: body.sucursalId } 
        }),
    });

    if (!empRes.ok) {
         // Rollback? ideally yes, but for now just fail.
         // TODO: delete user if employee creation fails?
         return NextResponse.json({ message: "Error creating employee record" }, { status: empRes.status });
    }

    const createdEmployee = await empRes.json();
    return NextResponse.json(createdEmployee);

  } catch (error) {
    console.error(error);
    return NextResponse.json({ message: "Error creating employee" }, { status: 500 });
  }
}
