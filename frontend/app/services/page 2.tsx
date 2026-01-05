import { getIronSession } from "iron-session";
import { cookies } from "next/headers";
import { sessionOptions, SessionData } from "@/lib/session";
import { redirect } from "next/navigation";
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Service } from "@/lib/definitions";

async function getServices(): Promise<Service[]> {
    const backendUrl = process.env.CATALOG_SERVICE_URL || "http://catalog-service:8080";
    try {
        const res = await fetch(`${backendUrl}/services`, {
            cache: 'no-store' // Dynamically fetch every time
        });
        if (!res.ok) {
            console.error("Failed to fetch services:", res.status, res.statusText);
            return [];
        }
        return res.json();
    } catch (e) {
        console.error("Error fetching services:", e);
        return [];
    }
}

export default async function ServicesPage() {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  
  // Basic route protection on the server
  if (!session.isLoggedIn) {
      redirect("/login");
  }

  const services = await getServices();

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-8">Available Services</h1>
      
      {services.length === 0 ? (
          <p className="text-muted-foreground">No services found or unable to connect to catalog.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {services.map((service) => (
                <Card key={service.id}>
                    <CardHeader>
                        <CardTitle>{service.nombre}</CardTitle>
                        <CardDescription>Duration: {service.duracion}</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <p>{service.descripcion}</p>
                    </CardContent>
                    <CardFooter>
                         <Button>Book Appointment</Button>
                    </CardFooter>
                </Card>
            ))}
        </div>
      )}
    </div>
  );
}
