"use client";

import { useEffect, useState } from "react";
import { Employee } from "@/lib/types-admin";
import { Button } from "@/components/ui/button";
import { Plus, Trash2 } from "lucide-react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogFooter,
} from "@/components/ui/dialog";

type Schedule = {
    id: number;
    diaLaboral: { id: number; nombre: string }; // or just nombre if flattened
    horaInicio: string; // "09:00:00"
    horaFin: string;
    // ...
};

// DTO from backend seems to flatten diaLaboral?
// Check EmpleadoResource: new dto.HorarioDto(..., diaLaboral.nombre, ...)
// Let's define based on what we see or expect.
type EmployeeSchedule = {
    id: number; // horario id
    idDiaLaboral: number;
    dia: string;
    horaInicio: string;
    horaFin: string;
};

export default function SchedulesPage() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [selectedEmployeeId, setSelectedEmployeeId] = useState<string | null>(null);
  const [employeeSchedules, setEmployeeSchedules] = useState<EmployeeSchedule[]>([]);
  const [availableSchedules, setAvailableSchedules] = useState<any[]>([]); // All global templates
  const [loading, setLoading] = useState(false);
  const [isAddDialogOpen, setIsAddDialogOpen] = useState(false);
  const [selectedScheduleId, setSelectedScheduleId] = useState<string>("");

  useEffect(() => {
    // Load employees
    fetch("/api/admin/employees")
      .then((res) => res.json())
      .then((data) => {
          setEmployees(data);
          if (data.length > 0) setSelectedEmployeeId(data[0].id.toString());
      });
      
    // Load global schedules (templates)
    fetch("/api/admin/schedules")
      .then((res) => {
          if (!res.ok) throw new Error("Failed to load");
          return res.json();
      })
      .then((data) => {
          if (Array.isArray(data)) {
            setAvailableSchedules(data);
          } else {
            console.error("Schedules data is not an array:", data);
            setAvailableSchedules([]);
          }
      })
      .catch(err => {
          console.error(err);
          setAvailableSchedules([]);
      });
  }, []);

  useEffect(() => {
      if (selectedEmployeeId) {
          setLoading(true);
          fetch(`/api/admin/employees/${selectedEmployeeId}/schedules`)
            .then((res) => res.json())
            .then((data) => setEmployeeSchedules(data))
            .finally(() => setLoading(false));
      }
  }, [selectedEmployeeId]);

  const handleAssign = async () => {
      if (!selectedEmployeeId || !selectedScheduleId) return;

      const res = await fetch(`/api/admin/employees/${selectedEmployeeId}/schedules`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ idHorario: parseInt(selectedScheduleId) })
      });
      
      if (res.ok) {
          setIsAddDialogOpen(false);
          // Refresh
          const updated = await fetch(`/api/admin/employees/${selectedEmployeeId}/schedules`).then(r => r.json());
          setEmployeeSchedules(updated);
      } else {
          alert("Error assigning or duplicate.");
      }
  };

  const handleRemove = async (scheduleId: number) => {
      if (!confirm("Remove this schedule?")) return;
      
      const res = await fetch(`/api/admin/employees/${selectedEmployeeId}/schedules/${scheduleId}`, {
          method: "DELETE"
      });
      
      if (res.ok) {
          setEmployeeSchedules(prev => prev.filter(s => s.id !== scheduleId));
      }
  };

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-3xl font-bold tracking-tight">Horarios</h2>
        <p className="text-muted-foreground">Administra los turnos de los empleados.</p>
      </div>

      {/* Employee Selector */}
      <div className="flex items-center gap-4">
          <span className="font-medium">Empleado:</span>
          <Select value={selectedEmployeeId || ""} onValueChange={setSelectedEmployeeId}>
            <SelectTrigger className="w-[280px]">
              <SelectValue placeholder="Selecciona un empleado" />
            </SelectTrigger>
            <SelectContent>
              {employees.map((emp) => (
                  <SelectItem key={emp.id} value={emp.id.toString()}>
                      {emp.nombre} {emp.primerApellido}
                  </SelectItem>
              ))}
            </SelectContent>
          </Select>
      </div>

      <div className="grid gap-6">
          <Card>
              <CardHeader className="flex flex-row items-center justify-between">
                  <CardTitle>Turnos Asignados</CardTitle>
                  <Button onClick={() => setIsAddDialogOpen(true)} disabled={!selectedEmployeeId}>
                      <Plus className="mr-2 h-4 w-4" /> Asignar Turno
                  </Button>
              </CardHeader>
              <CardContent>
                  {loading ? (
                      <div className="text-center py-4">Cargando...</div>
                  ) : employeeSchedules.length === 0 ? (
                      <div className="text-center py-4 text-muted-foreground">No hay turnos asignados.</div>
                  ) : (
                      <div className="space-y-2">
                          {employeeSchedules.map((sch) => (
                              <div key={sch.id} className="flex items-center justify-between border p-3 rounded-md">
                                  <div>
                                      <p className="font-semibold">{sch.dia}</p>
                                      <p className="text-sm text-muted-foreground">
                                          {sch.horaInicio} - {sch.horaFin}
                                      </p>
                                  </div>
                                  <Button variant="ghost" size="icon" className="text-red-500" onClick={() => handleRemove(sch.id)}>
                                      <Trash2 className="h-4 w-4" />
                                  </Button>
                              </div>
                          ))}
                      </div>
                  )}
              </CardContent>
          </Card>
      </div>
      
      <Dialog open={isAddDialogOpen} onOpenChange={setIsAddDialogOpen}>
          <DialogContent>
              <DialogHeader>
                  <DialogTitle>Asignar Turno</DialogTitle>
              </DialogHeader>
              <div className="py-4">
                  <Select onValueChange={setSelectedScheduleId}>
                      <SelectTrigger>
                          <SelectValue placeholder="Selecciona un horario" />
                      </SelectTrigger>
                      <SelectContent>
                          {availableSchedules.map((s) => (
                              <SelectItem key={s.id} value={s.id.toString()}>
                                  {s.nombreDia || "Dia?"} | {s.horaInicio} - {s.horaFin} {s.nombreSucursal ? `(${s.nombreSucursal})` : ""}
                              </SelectItem>
                          ))}
                      </SelectContent>
                  </Select>
              </div>
              <DialogFooter>
                  <Button onClick={handleAssign}>Asignar</Button>
              </DialogFooter>
          </DialogContent>
      </Dialog>
    </div>
  );
}
