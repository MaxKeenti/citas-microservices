"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table";
import { Cita } from "@/lib/types-appointment";

export default function EmployeeDashboardPage() {
    const [appointments, setAppointments] = useState<Cita[]>([]);
    const [loading, setLoading] = useState(true);
    const [services, setServices] = useState<Record<number, string>>({});
    const [employees, setEmployees] = useState<Record<number, string>>({});

    const fetchAppointments = async () => {
        try {
            // Reuses the admin API which filters by employee ID if user is employee
            const res = await fetch("/api/admin/appointments");
            if (res.ok) {
                const data = await res.json();
                console.log("Employee Appointments Data:", data);
                setAppointments(data);
            }
        } catch (e) {
            console.error(e);
        } finally {
            setLoading(false);
        }
    };

    const fetchLookups = async () => {
        // Services
        fetch("/api/services").then(r => r.json()).then(d => {
            const map: Record<number, string> = {};
            if (Array.isArray(d)) d.forEach((s: any) => map[s.id] = s.nombre);
            setServices(map);
        });
        // Employees (needed to show name, though likely only self)
        fetch("/api/admin/employees").then(r => r.json()).then(d => {
            const map: Record<number, string> = {};
            if (Array.isArray(d)) d.forEach((e: any) => map[e.id] = `${e.nombre} ${e.primerApellido}`);
            setEmployees(map);
        });
    };

    useEffect(() => {
        fetchAppointments();
        fetchLookups();
    }, []);

    const updateStatus = async (id: number, statusId: number) => {
        try {
            const res = await fetch(`/api/appointments/${id}/status`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ id: statusId })
            });
            if (res.ok) {
                fetchAppointments();
            } else {
                alert("Error actualizando estado");
            }
        } catch (e) {
            console.error(e);
        }
    };

    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <h2 className="text-3xl font-bold tracking-tight">Mis Citas Asignadas</h2>
            </div>

            <div className="border rounded-md bg-white p-4 shadow-sm">
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>ID</TableHead>
                            <TableHead>Fecha/Hora</TableHead>
                            <TableHead>Servicio</TableHead>
                            <TableHead>Estado</TableHead>
                            <TableHead>Acciones</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {loading ? (
                            <TableRow><TableCell colSpan={5} className="text-center h-24">Cargando...</TableCell></TableRow>
                        ) : appointments.length === 0 ? (
                            <TableRow><TableCell colSpan={5} className="text-center h-24">No tienes citas asignadas.</TableCell></TableRow>
                        ) : (
                            appointments.map((cita) => (
                                <TableRow key={cita.id}>
                                    <TableCell>{cita.id}</TableCell>
                                    <TableCell>
                                        {new Date(cita.fechaHora).toLocaleString('es-MX', {
                                            dateStyle: 'short', timeStyle: 'short'
                                        })}
                                    </TableCell>
                                    <TableCell>{services[cita.idServicio] || `ID: ${cita.idServicio}`}</TableCell>
                                    <TableCell>
                                        <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${cita.estado?.id === 3 ? "bg-red-100 text-red-800" :
                                            cita.estado?.id === 4 ? "bg-green-100 text-green-800" :
                                                "bg-blue-100 text-blue-800"
                                            }`}>
                                            {cita.estado?.nombre || "N/A"}
                                        </span>
                                    </TableCell>
                                    <TableCell>
                                        <div className="flex gap-2">
                                            {cita.estado?.id === 1 && (
                                                <>
                                                    <Button size="sm" variant="outline" onClick={() => updateStatus(cita.id, 2)}>Confirmar</Button>
                                                </>
                                            )}
                                            {(cita.estado?.id === 1 || cita.estado?.id === 2) && (
                                                <>
                                                    <Button size="sm" variant="default" onClick={() => updateStatus(cita.id, 4)}>Completar</Button>
                                                    <Button size="sm" variant="destructive" onClick={() => updateStatus(cita.id, 5)}>No Asistió</Button>
                                                </>
                                            )}
                                        </div>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </div>
        </div>
    );
}
