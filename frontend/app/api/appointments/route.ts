
import { NextRequest, NextResponse } from "next/server";
import { sessionOptions, SessionData } from "@/lib/session";
import { getIronSession } from "iron-session";

export async function GET(request: NextRequest) {
  const session = await getIronSession<SessionData>(request, NextResponse.next(), sessionOptions);

  if (!session.isLoggedIn || !session.user) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  const serviceUrl = process.env.APPOINTMENT_SERVICE_URL;
  if (!serviceUrl) {
    return NextResponse.json({ message: "Service URL not configured" }, { status: 500 });
  }

  try {
    const res = await fetch(`${serviceUrl}/appointments?personaId=${session.user.id}`, {
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!res.ok) {
        // If 404, maybe return empty list? Or propagate error?
        // Appointment service shouldn't 404 on list, but 500 maybe.
        return NextResponse.json({ message: await res.text() }, { status: res.status });
    }

    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    console.error("Error fetching appointments:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}

export async function POST(request: NextRequest) {
  const session = await getIronSession<SessionData>(request, NextResponse.next(), sessionOptions);

  if (!session.isLoggedIn || !session.user) {
    return NextResponse.json({ message: "Unauthorized" }, { status: 401 });
  }

  const serviceUrl = process.env.APPOINTMENT_SERVICE_URL;

  try {
    const body = await request.json();
    const payload = {
        ...body,
        idPersona: session.user.id,
        idSucursal: body.idSucursal || 1, 
        idListaPrecio: body.idListaPrecio || 1 
    };

    const res = await fetch(`${serviceUrl}/appointments`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
        return NextResponse.json({ message: await res.text() }, { status: res.status });
    }

    const data = await res.json();
    return NextResponse.json(data, { status: 201 });
  } catch (error) {
    console.error("Error creating appointment:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}
