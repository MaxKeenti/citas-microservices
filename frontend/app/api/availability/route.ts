
import { NextRequest, NextResponse } from "next/server";

export async function GET(request: NextRequest) {
  const searchParams = request.nextUrl.searchParams;
  const date = searchParams.get("date");
  const employeeId = searchParams.get("employeeId");

  if (!date || !employeeId) {
    return NextResponse.json({ message: "Missing date or employeeId" }, { status: 400 });
  }

  const serviceUrl = process.env.APPOINTMENT_SERVICE_URL;
  if (!serviceUrl) {
    return NextResponse.json({ message: "Service URL not configured" }, { status: 500 });
  }

  try {
    const res = await fetch(`${serviceUrl}/appointments?empleadoId=${employeeId}&date=${date}`, {
      headers: {
        "Content-Type": "application/json",
      },
      cache: 'no-store'
    });

    if (!res.ok) {
        return NextResponse.json({ message: await res.text() }, { status: res.status });
    }

    const data = await res.json();
    return NextResponse.json(data);
  } catch (error) {
    console.error("Error fetching availability:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}
