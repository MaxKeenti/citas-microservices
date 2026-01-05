
import { NextResponse } from "next/server";

export async function GET() {
  const serviceUrl = process.env.HRESOURCES_SERVICE_URL;
  if (!serviceUrl) {
    return NextResponse.json({ message: "Service URL not configured" }, { status: 500 });
  }

  try {
    const res = await fetch(`${serviceUrl}/employees`, {
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
    console.error("Error fetching employees:", error);
    return NextResponse.json({ message: "Internal Server Error" }, { status: 500 });
  }
}
