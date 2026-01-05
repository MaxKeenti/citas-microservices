"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import Link from "next/link";
import { useRouter } from "next/navigation";

// Define strict types matching the Backend Cita model
interface Cita {
  id: number;
  fechaHora: string;
  duracion: number;
  idServicio: number;
  // We might want enriched data later (Service Name, Branch Name), 
  // currently we only have IDs.
}

export default function MyAppointmentsPage() {
  const [appointments, setAppointments] = useState<Cita[]>([]);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  const [services, setServices] = useState<Record<number, string>>({});
  const [employees, setEmployees] = useState<Record<number, string>>({});

  useEffect(() => {
    // 1. Fetch Appointments
    fetch("/api/appointments")
      .then((res) => {
        if (res.status === 401) {
          router.push("/login");
          return null;
        }
        return res.json();
      })
      .then((data) => {
        if (data && Array.isArray(data)) {
            setAppointments(data);
        }
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });

    // 2. Fetch Services for lookup
    fetch("/api/services")
        .then(res => res.json())
        .then(data => {
            if (Array.isArray(data)) {
                // Create map ID -> Name
                const map: Record<number, string> = {};
                data.forEach((s: any) => map[s.id] = s.nombre);
                setServices(map);
            }
        });

    // 3. Fetch Employees for lookup
    fetch("/api/employees")
        .then(res => res.json())
        .then(data => {
            if (Array.isArray(data)) {
                 const map: Record<number, string> = {};
                 data.forEach((e: any) => map[e.id] = `${e.nombre} ${e.primerApellido}`);
                 setEmployees(map);
            }
        });

  }, [router]);

  return (
    <div className="container mx-auto p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold tracking-tight">Mis Citas</h1>
        <Button asChild>
          <Link href="/appointments/new">Nueva Cita</Link>
        </Button>
      </div>

      {loading ? (
        <div className="flex justify-center p-8">Cargando citas...</div>
      ) : appointments.length === 0 ? (
        <Card className="text-center p-8 text-muted-foreground">
            <p>No tienes citas programadas.</p>
        </Card>
      ) : (
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          {appointments.map((cita) => (
            <Card key={cita.id}>
              <CardHeader>
                <CardTitle>Cita #{cita.id}</CardTitle>
                <CardDescription>
                  {new Date(cita.fechaHora).toLocaleString('es-MX', { 
                      dateStyle: 'full', 
                      timeStyle: 'short' 
                  })}
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-1">
                    <p><strong>Servicio:</strong> {services[cita.idServicio] || `ID: ${cita.idServicio}`}</p>
                    <p><strong>Duración:</strong> {cita.duracion} min</p>
                    {/* Assuming Cita has idEmpleado, if not we might default or ignore */}
                    { (cita as any).idEmpleado && (
                         <p><strong>Profesional:</strong> {employees[(cita as any).idEmpleado] || `ID: ${(cita as any).idEmpleado}`}</p>
                    )}
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
