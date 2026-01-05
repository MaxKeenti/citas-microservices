"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Employee } from "@/lib/definitions";
import { Input } from "@/components/ui/input";

type DiaDescanso = {
    id: number;
    idEmpleado: number;
    fecha: string;
};

export default function DaysOffPage() {
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [selectedEmployeeId, setSelectedEmployeeId] = useState<string>("");
    const [daysOff, setDaysOff] = useState<DiaDescanso[]>([]);
    const [selectedDate, setSelectedDate] = useState<string>("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        fetch("/api/admin/employees")
            .then((res) => res.json())
            .then((data) => setEmployees(data))
            .catch((err) => console.error(err));
    }, []);

    useEffect(() => {
        if (selectedEmployeeId) {
            fetchDaysOff(selectedEmployeeId);
        } else {
            setDaysOff([]);
        }
    }, [selectedEmployeeId]);

    const fetchDaysOff = async (empId: string) => {
        setLoading(true);
        try {
            const res = await fetch(`/api/days-off/employee/${empId}`);
            if (res.ok) {
                const data = await res.json();
                setDaysOff(data);
            }
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    const handleAddDayOff = async () => {
        if (!selectedEmployeeId || !selectedDate) return;

        try {
            const res = await fetch("/api/days-off", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    idEmpleado: parseInt(selectedEmployeeId),
                    fecha: selectedDate, // YYYY-MM-DD from input
                }),
            });

            if (res.ok) {
                fetchDaysOff(selectedEmployeeId);
                alert("Día de descanso agregado");
                setSelectedDate(""); // Reset date
            } else {
                alert("Error al agregar (¿posible duplicado?)");
            }
        } catch (error) {
            console.error(error);
        }
    };

    const handleDeleteDayOff = async (id: number) => {
        if (!confirm("¿Eliminar día de descanso?")) return;
        try {
            const res = await fetch(`/api/days-off/${id}`, {
                method: "DELETE",
            });
            if (res.ok) {
                fetchDaysOff(selectedEmployeeId);
            }
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="space-y-6">
            <h2 className="text-3xl font-bold tracking-tight">Gestión de Días de Descanso</h2>

            <div className="flex gap-4 items-start">
                <Card className="w-1/3">
                    <CardHeader>
                        <CardTitle>Seleccionar Empleado</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <Select onValueChange={setSelectedEmployeeId}>
                            <SelectTrigger>
                                <SelectValue placeholder="Empleado" />
                            </SelectTrigger>
                            <SelectContent>
                                {employees.map((emp) => (
                                    <SelectItem key={emp.id} value={emp.id.toString()}>
                                        {emp.nombre} {emp.primerApellido}
                                    </SelectItem>
                                ))}
                            </SelectContent>
                        </Select>
                    </CardContent>
                </Card>

                {selectedEmployeeId && (
                    <Card className="flex-1">
                        <CardHeader>
                            <CardTitle>Administrar Días</CardTitle>
                        </CardHeader>
                        <CardContent className="flex gap-8">
                            <div className="w-1/2">
                                <label className="block text-sm font-medium mb-2">Seleccionar Fecha</label>
                                <Input
                                    type="date"
                                    value={selectedDate}
                                    onChange={(e) => setSelectedDate(e.target.value)}
                                    className="w-full"
                                />
                                <Button className="mt-4 w-full" onClick={handleAddDayOff} disabled={!selectedDate}>
                                    Agregar como Día de Descanso
                                </Button>
                            </div>

                            <div className="flex-1">
                                <h3 className="font-semibold mb-2">Días Registrados</h3>
                                {loading ? (
                                    <p>Cargando...</p>
                                ) : daysOff.length === 0 ? (
                                    <p className="text-muted-foreground">No hay días de descanso registrados.</p>
                                ) : (
                                    <ul className="space-y-2 max-h-64 overflow-y-auto">
                                        {daysOff.map((day) => (
                                            <li key={day.id} className="flex justify-between items-center border p-2 rounded">
                                                <span>{day.fecha}</span>
                                                <Button variant="destructive" size="sm" onClick={() => handleDeleteDayOff(day.id)}>
                                                    Eliminar
                                                </Button>
                                            </li>
                                        ))}
                                    </ul>
                                )}
                            </div>
                        </CardContent>
                    </Card>
                )}
            </div>
        </div>
    );
}
