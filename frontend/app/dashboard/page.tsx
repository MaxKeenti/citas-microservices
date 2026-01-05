import { getIronSession } from "iron-session";
import { cookies } from "next/headers";
import { sessionOptions, SessionData } from "@/lib/session";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import Link from "next/link";
import { redirect } from "next/navigation";

export default async function DashboardPage() {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  
  if (!session.isLoggedIn || !session.user) {
      redirect("/login");
  }

  const user = session.user;

  return (
    <div className="container mx-auto py-10 px-4">
      <div className="mb-8">
          <h1 className="text-3xl font-bold">Panel de Control</h1>
          <p className="text-muted-foreground mt-2">Bienvenido de nuevo, {user.nombre} {user.primerApellido}.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {/* Quick Actions */}
        <Card>
            <CardHeader>
                <CardTitle>Acciones Rápidas</CardTitle>
                <CardDescription>Gestiona tus citas fácilmente.</CardDescription>
            </CardHeader>
            <CardContent className="flex flex-col gap-3">
                 <Button asChild className="w-full justify-start">
                    <Link href="/appointments/new">
                        <span className="mr-2">+</span> Reservar Nueva Cita
                    </Link>
                 </Button>
                 <Button asChild variant="outline" className="w-full justify-start">
                    <Link href="/appointments">
                        <span className="mr-2">📅</span> Ver Mis Citas
                    </Link>
                 </Button>
            </CardContent>
        </Card>

        {/* Profile / Status (Placeholder) */}
        <Card>
            <CardHeader>
                <CardTitle>Mi Perfil</CardTitle>
                <CardDescription>Tus datos registrados.</CardDescription>
            </CardHeader>
             <CardContent>
                 <div className="space-y-1 text-sm">
                     <p><strong>Usuario:</strong> {user.nombre} {user.primerApellido}</p>
                     {/* Email in legacy was username, assuming it's email now */}
                     {/* <p><strong>Email:</strong> {user.email || 'No disponible'}</p> */}
                 </div>
                 <div className="mt-4">
                    <Button asChild variant="ghost" size="sm" className="px-0">
                         {/* Link to profile edit if implemented, or just placeholder */}
                         <span className="text-muted-foreground cursor-not-allowed">Editar Perfil (Próximamente)</span>
                    </Button>
                 </div>
             </CardContent>
        </Card>

        {/* Services Promo */}
        <Card className="bg-primary text-primary-foreground">
            <CardHeader>
                <CardTitle className="text-white">Descubre Nuestros Servicios</CardTitle>
            </CardHeader>
            <CardContent>
                <p className="mb-4 text-white/90">Echa un vistazo a los nuevos cortes y estilos de temporada.</p>
                <Button asChild variant="secondary" className="w-full">
                    <Link href="/services">Ver Catálogo</Link>
                </Button>
            </CardContent>
        </Card>
      </div>
    </div>
  );
}
