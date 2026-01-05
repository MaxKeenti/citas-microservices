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

  useEffect(() => {
    fetch("/api/appointments")
      .then((res) => {
        if (res.status === 401) {
          router.push("/login");
          return null;
        }
        return res.json();
      })
      .then((data) => {
        if (data) {
          // If it's an array
          if (Array.isArray(data)) {
            setAppointments(data);
          }
        }
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
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
                  {new Date(cita.fechaHora).toLocaleString()}
                </CardDescription>
              </CardHeader>
              <CardContent>
                <p>Duración: {cita.duracion} min</p>
                <p>Servicio ID: {cita.idServicio}</p>
                 {/* TODO: Resolve Service Name from Catalog Service */}
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
