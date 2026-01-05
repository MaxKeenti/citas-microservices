"use client";

import { useEffect, useState } from "react";
import { Employee, Branch } from "@/lib/types-admin";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { EmployeeDialog } from "@/components/employee-dialog";

export default function EmployeesPage() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [branches, setBranches] = useState<Branch[]>([]);
  const [loading, setLoading] = useState(true);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const fetchEmployees = async () => {
    try {
      const res = await fetch("/api/admin/employees");
      const data = await res.json();
      setEmployees(data);
    } catch (error) {
      console.error("Error fetching employees:", error);
    } finally {
      setLoading(false);
    }
  };
  
  // Mock fetch branches or create API for it. 
  // For now, I'll rely on hardcoded or I need to expose /api/admin/branches. 
  // Let's assume there is at least one brnach from seed. 
  const fetchBranches = async () => {
      // Temporary hardcode or fetch if exposed.
      // HResources exposes /sucursales publicly?
      // Let's create a quick API fetcher or mock it for now as "Centro" id:1
      setBranches([{ id: 1, nombre: "Sucursal Centro" }]);
  };

  useEffect(() => {
    fetchEmployees();
    fetchBranches();
  }, []);

  const handleSave = async (data: any) => {
      await fetch("/api/admin/employees", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });
      fetchEmployees();
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-bold tracking-tight">Empleados</h2>
          <p className="text-muted-foreground">Gestiona el personal y sus asignaciones.</p>
        </div>
        <Button onClick={() => setIsDialogOpen(true)}>
          <Plus className="mr-2 h-4 w-4" /> Nuevo Empleado
        </Button>
      </div>

      <div className="border rounded-md">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>Nombre Completo</TableHead>
              <TableHead>Sucursal</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loading ? (
                <TableRow>
                    <TableCell colSpan={3} className="text-center h-24">Cargando...</TableCell>
                </TableRow>
            ) : employees.length === 0 ? (
                <TableRow>
                    <TableCell colSpan={3} className="text-center h-24">No hay empleados registrados.</TableCell>
                </TableRow>
            ) : (
                employees.map((emp) => (
                    <TableRow key={emp.id}>
                        <TableCell>{emp.id}</TableCell>
                        <TableCell className="font-medium">{emp.nombre} {emp.primerApellido} {emp.segundoApellido}</TableCell>
                        <TableCell>{emp.sucursal?.nombre || "Sin Asignar"}</TableCell>
                    </TableRow>
                ))
            )}
          </TableBody>
        </Table>
      </div>

      <EmployeeDialog 
        open={isDialogOpen} 
        onOpenChange={setIsDialogOpen}
        onSave={handleSave}
        branches={branches}
      />
    </div>
  );
}
