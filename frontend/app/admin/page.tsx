"use client";

import { useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Activity, Users, Calendar, Clock } from "lucide-react";

export default function AdminDashboard() {
  const [stats, setStats] = useState({
    totalAppointments: 0,
    appointmentsToday: 0,
    pendingConfirmations: 0,
    activeUsers: 0
  });

  const [recentAppointments, setRecentAppointments] = useState<any[]>([]);

  useEffect(() => {
    // Fetch Stats
    fetch("/api/admin/stats")
      .then(res => res.json())
      .then(data => setStats(data))
      .catch(err => console.error(err));

    // Fetch Recent Appointments
    fetch("/api/admin/appointments")
      .then(res => res.json())
      .then(data => {
        if (Array.isArray(data)) {
          // Sort by date desc and take 5
          setRecentAppointments(data.sort((a: any, b: any) => new Date(b.fechaHora).getTime() - new Date(a.fechaHora).getTime()).slice(0, 5));
        }
      })
      .catch(err => console.error(err));
  }, []);

  return (
    <div className="space-y-8">
      <div>
        <h2 className="text-3xl font-bold tracking-tight">Dashboard</h2>
        <p className="text-muted-foreground">Bienvenido al panel de administración.</p>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Citas Totales</CardTitle>
            <Calendar className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.totalAppointments}</div>
            <p className="text-xs text-muted-foreground">Registradas en el sistema</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Citas Hoy</CardTitle>
            <Clock className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">+{stats.appointmentsToday}</div>
            <p className="text-xs text-muted-foreground">Para el día de hoy</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Pendientes</CardTitle>
            <Activity className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.pendingConfirmations}</div>
            <p className="text-xs text-muted-foreground">Requieren confirmación</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Usuarios Activos</CardTitle>
            <Users className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{stats.activeUsers}</div>
            <p className="text-xs text-muted-foreground">Clientes y empleados</p>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <Card className="col-span-4">
          <CardHeader>
            <CardTitle>Resumen</CardTitle>
          </CardHeader>
          <CardContent className="pl-2">
            <div className="h-[200px] flex items-center justify-center text-muted-foreground">
              (Gráfica de actividad no implementada)
            </div>
          </CardContent>
        </Card>
        <Card className="col-span-3">
          <CardHeader>
            <CardTitle>Citas Recientes</CardTitle>
            <CardDescription>
              Últimas 5 citas registradas.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {recentAppointments.length === 0 ? (
                <p className="text-sm text-muted-foreground">No hay actividad reciente.</p>
              ) : (
                recentAppointments.map((cita) => (
                  <div key={cita.id} className="flex items-center">
                    <span className={`relative flex h-2 w-2 mr-2`}>
                      <span className={`animate-ping absolute inline-flex h-full w-full rounded-full opacity-75 ${cita.estado?.id === 1 ? 'bg-sky-400' : 'bg-gray-400'}`}></span>
                      <span className={`relative inline-flex rounded-full h-2 w-2 ${cita.estado?.id === 1 ? 'bg-sky-500' : 'bg-gray-500'}`}></span>
                    </span>
                    <div className="ml-2 space-y-1">
                      <p className="text-sm font-medium leading-none">Cita #{cita.id}</p>
                      <p className="text-xs text-muted-foreground">
                        {new Date(cita.fechaHora).toLocaleString()} - {cita.estado?.nombre}
                      </p>
                    </div>
                  </div>
                ))
              )}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
