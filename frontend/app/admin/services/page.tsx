"use client";

import { useEffect, useState } from "react";
import { Service } from "@/lib/definitions";
import { Button } from "@/components/ui/button";
import { Plus, Pencil, Trash2 } from "lucide-react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { ServiceDialog } from "@/components/service-dialog";
import { Badge } from "@/components/ui/badge";

export default function ServicesPage() {
  const [services, setServices] = useState<Service[]>([]);
  const [loading, setLoading] = useState(true);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [editingService, setEditingService] = useState<Service | null>(null);

  const fetchServices = async () => {
    try {
      const res = await fetch("/api/admin/services");
      const data = await res.json();
      setServices(data);
    } catch (error) {
      console.error("Error fetching services:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchServices();
  }, []);

  const handleCreate = () => {
    setEditingService(null);
    setIsDialogOpen(true);
  };

  const handleEdit = (service: Service) => {
    setEditingService(service);
    setIsDialogOpen(true);
  };

  const handleDelete = async (id: number) => {
    if (!confirm("¿Estás seguro de eliminar este servicio?")) return;
    
    try {
        await fetch(`/api/admin/services/${id}`, { method: "DELETE" });
        fetchServices();
    } catch(err) {
        console.error(err);
    }
  };

  const handleSave = async (service: Partial<Service>) => {
    if (editingService) {
      // Update
      await fetch(`/api/admin/services/${editingService.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(service),
      });
    } else {
      // Create
      await fetch("/api/admin/services", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(service),
      });
    }
    fetchServices();
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-bold tracking-tight">Servicios</h2>
          <p className="text-muted-foreground">Administra el catálogo de servicios.</p>
        </div>
        <Button onClick={handleCreate}>
          <Plus className="mr-2 h-4 w-4" /> Nuevo Servicio
        </Button>
      </div>

      <div className="border rounded-md">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>Nombre</TableHead>
              <TableHead>Descripción</TableHead>
              <TableHead>Duración</TableHead>
              <TableHead>Estado</TableHead>
              <TableHead className="text-right">Acciones</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loading ? (
                <TableRow>
                    <TableCell colSpan={6} className="text-center h-24">Cargando...</TableCell>
                </TableRow>
            ) : services.length === 0 ? (
                <TableRow>
                    <TableCell colSpan={6} className="text-center h-24">No hay servicios registrados.</TableCell>
                </TableRow>
            ) : (
                services.map((service) => (
                    <TableRow key={service.id}>
                        <TableCell>{service.id}</TableCell>
                        <TableCell className="font-medium">{service.nombre}</TableCell>
                        <TableCell>{service.descripcion}</TableCell>
                        <TableCell>{service.duracion} min</TableCell>
                        <TableCell>
                            <Badge variant={service.activo ? "default" : "secondary"}>
                                {service.activo ? "Activo" : "Inactivo"}
                            </Badge>
                        </TableCell>
                        <TableCell className="text-right">
                        <Button variant="ghost" size="icon" onClick={() => handleEdit(service)}>
                            <Pencil className="h-4 w-4" />
                        </Button>
                        <Button variant="ghost" size="icon" className="text-red-500 hover:text-red-600" onClick={() => handleDelete(service.id)}>
                            <Trash2 className="h-4 w-4" />
                        </Button>
                        </TableCell>
                    </TableRow>
                ))
            )}
          </TableBody>
        </Table>
      </div>

      <ServiceDialog 
        open={isDialogOpen} 
        onOpenChange={setIsDialogOpen}
        service={editingService}
        onSave={handleSave}
      />
    </div>
  );
}
