
import Link from 'next/link';
import { getIronSession } from 'iron-session';
import { cookies } from 'next/headers';
import { sessionOptions, SessionData } from '@/lib/session';
import { Button } from './ui/button';

export default async function Navbar() {
  const session = await getIronSession<SessionData>(await cookies(), sessionOptions);
  const isLoggedIn = session.isLoggedIn;
  const user = session.user;

  return (
    <nav className="border-b bg-background">
      <div className="container mx-auto flex h-16 items-center justify-between px-4">
        {/* Logo */}
        <Link href="/" className="text-xl font-bold tracking-tight text-primary">
          BarberShop
        </Link>

        {/* Navigation Links */}
        <div className="flex items-center gap-6">
            {!isLoggedIn ? (
                <>
                    <Link href="/services" className="text-sm font-medium hover:underline">
                        Servicios
                    </Link>
                    <div className="flex items-center gap-2">
                        <Button variant="ghost" asChild>
                            <Link href="/login">Iniciar Sesión</Link>
                        </Button>
                        <Button asChild>
                            <Link href="/register">Registrarse</Link>
                        </Button>
                    </div>
                </>
            ) : (
                <>
                    <Link href="/dashboard" className="text-sm font-medium hover:underline">
                        Panel
                    </Link>
                    {user?.roles?.includes("admin") && (
                        <Link href="/admin" className="text-sm font-medium hover:underline text-indigo-600">
                            Administración
                        </Link>
                    )}
                    <Link href="/appointments" className="text-sm font-medium hover:underline">
                        Mis Citas
                    </Link>
                     <Link href="/appointments/new" className="text-sm font-medium hover:underline">
                        Reservar
                    </Link>
                    <div className="flex items-center gap-4">
                        <span className="text-sm text-muted-foreground">
                            Hola, {user?.nombre} {user?.primerApellido}
                        </span>
                         {/* Logout via API route */}
                         <form action="/api/auth/logout" method="POST">
                            <Button variant="outline" size="sm" type="submit">
                                Cerrar Sesión
                            </Button>
                        </form>
                    </div>
                </>
            )}
        </div>
      </div>
    </nav>
  );
}
