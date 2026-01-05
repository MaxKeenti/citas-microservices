
import Image from "next/image";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import { getServices } from "@/lib/api-service";

export default async function Home() {
  const services = await getServices();

  return (
    <div className="flex flex-col min-h-screen">
      {/* Hero Section */}
      <section className="w-full py-24 lg:py-32 xl:py-48 bg-black text-white relative">
        <div className="absolute inset-0 opacity-20">
          {/* Placeholder for background image */}
          <Image src="/hero-barber.jpg" alt="Barber Shop" fill className="object-cover" />
        </div>
        <div className="container relative z-10 px-4 md:px-6 text-center">
          <div className="flex flex-col items-center space-y-4">
            <h1 className="text-4xl font-bold tracking-tighter sm:text-5xl md:text-6xl lg:text-7xl">
              BarberShop Services
            </h1>
            <p className="mx-auto max-w-[700px] text-gray-300 md:text-xl">
              Estilo clásico para el hombre moderno. Agenda tu cita y vive la experiencia.
            </p>
            <div className="space-x-4">
              <Button asChild size="lg" className="bg-white text-black hover:bg-gray-200">
                <Link href="/register">Registrarse</Link>
              </Button>
              <Button asChild variant="outline" size="lg" className="bg-white text-black hover:bg-gray-200">
                <Link href="/login">Iniciar Sesión</Link>
              </Button>
            </div>
          </div>
        </div>
      </section>

      {/* Services Section */}
      <section className="w-full py-12 md:py-24 lg:py-32 bg-gray-100 dark:bg-gray-900">
        <div className="container px-4 md:px-6">
          <div className="flex flex-col items-center justify-center space-y-4 text-center">
            <h2 className="text-3xl font-bold tracking-tighter sm:text-5xl">Nuestros Servicios</h2>
            <p className="max-w-[900px] text-muted-foreground md:text-xl/relaxed lg:text-base/relaxed xl:text-xl/relaxed">
              Ofrecemos una amplia gama de servicios de barbería de alta calidad.
            </p>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mt-12">
            {services.slice(0, 3).map((service) => (
              <div key={service.id} className="flex flex-col items-center space-y-2 border p-6 rounded-lg bg-background shadow-sm">
                <div className="p-2 bg-black text-white rounded-full">
                  {/* Generic icon for now since we don't store icons in DB yet */}
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><circle cx="12" cy="12" r="10" /><path d="M12 6v6l4 2" /></svg>
                </div>
                <h3 className="text-xl font-bold">{service.nombre}</h3>
                <p className="text-muted-foreground text-center">{service.descripcion}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="w-full py-6 bg-black text-white">
        <div className="container px-4 md:px-6 flex flex-col md:flex-row justify-between items-center">
          <p className="text-xs text-gray-400">© 2026 BarberShop Inc. Todos los derechos reservados.</p>
          <nav className="flex gap-4 sm:gap-6 mt-4 md:mt-0">
            <Link className="text-xs hover:underline underline-offset-4" href="#">Términos de Servicio</Link>
            <Link className="text-xs hover:underline underline-offset-4" href="#">Privacidad</Link>
          </nav>
        </div>
      </footer>
    </div>
  );
}
