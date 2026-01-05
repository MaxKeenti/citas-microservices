import { getIronSession } from "iron-session";
import { cookies } from "next/headers";
import { sessionOptions, SessionData } from "@/lib/session";
import Link from "next/link";
// import { redirect } from "next/navigation";
import { Card, CardContent, CardDescription, CardHeader, CardTitle, CardFooter } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Service } from "@/lib/definitions";

import { getServices } from "@/lib/api-service";


export default async function ServicesPage() {
    const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
    // Page is public, no redirect.

    const services = await getServices();
    const isLoggedIn = session.isLoggedIn;

    return (
        <div className="container mx-auto py-10">
            <h1 className="text-3xl font-bold mb-8 text-center md:text-left">Nuestros Servicios</h1>

            {services.length === 0 ? (
                <p className="text-muted-foreground">No se encontraron servicios disponibles en este momento.</p>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {services.map((service) => (
                        <Card key={service.id} className="flex flex-col">
                            <CardHeader>
                                <CardTitle>{service.nombre}</CardTitle>
                                <CardDescription>Duración: {service.duracion} minutos</CardDescription>
                            </CardHeader>
                            <CardContent className="flex-1">
                                <p>{service.descripcion}</p>
                            </CardContent>
                            <CardFooter>
                                <div className="w-full">
                                    {isLoggedIn ? (
                                        <Button asChild className="w-full">
                                            <Link href="/appointments/new">Reservar Cita</Link>
                                        </Button>
                                    ) : (
                                        <Button asChild variant="outline" className="w-full">
                                            <Link href="/login">Iniciar Sesión para Reservar</Link>
                                        </Button>
                                    )}
                                </div>
                            </CardFooter>
                        </Card>
                    ))}
                </div>
            )}
        </div>
    );
}
