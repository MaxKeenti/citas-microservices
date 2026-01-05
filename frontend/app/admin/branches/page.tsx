"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Plus, Trash2, Edit } from "lucide-react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogFooter,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import dynamic from 'next/dynamic';

// Dynamic import for Map to avoid SSR issues
const BranchMap = dynamic(() => import("@/components/branch-map"), { ssr: false });

type Branch = {
    id: number;
    nombre: string;
    lat?: number;
    lng?: number;
    idEstablecimiento?: number;
};

export default function BranchesPage() {
  const [branches, setBranches] = useState<Branch[]>([]);
  const [loading, setLoading] = useState(true);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [form, setForm] = useState<Branch>({ id: 0, nombre: "", lat: 19.4326, lng: -99.1332 });
  const [isEditing, setIsEditing] = useState(false);

  const fetchBranches = async () => {
      try {
          const res = await fetch("/api/admin/branches");
          if (!res.ok) throw new Error("Failed");
          const data = await res.json();
          setBranches(data);
      } catch (e) {
          console.error(e);
      } finally {
          setLoading(false);
      }
  };

  useEffect(() => {
      fetchBranches();
  }, []);

  const handleEdit = (b: Branch) => {
      setForm({ ...b, lat: b.lat || 19.4326, lng: b.lng || -99.1332 });
      setIsEditing(true);
      setIsDialogOpen(true);
  };

  const handleCreate = () => {
      setForm({ id: 0, nombre: "", lat: 19.4326, lng: -99.1332 });
      setIsEditing(false);
      setIsDialogOpen(true);
  };

  const handleSave = async () => {
      const url = isEditing && form.id ? `/api/admin/branches/${form.id}` : "/api/admin/branches";
      const method = isEditing ? "PUT" : "POST";
      
      const res = await fetch(url, {
          method,
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(form)
      });
      
      if (res.ok) {
          setIsDialogOpen(false);
          fetchBranches();
      } else {
          alert("Error saving branch");
      }
  };

  const handleDelete = async (id: number) => {
      if (!confirm("Are you sure?")) return;
      await fetch(`/api/admin/branches/${id}`, { method: "DELETE" });
      fetchBranches();
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-bold tracking-tight">Sucursales</h2>
          <p className="text-muted-foreground">Gestiona las ubicaciones y sucursales.</p>
        </div>
        <Button onClick={handleCreate}>
          <Plus className="mr-2 h-4 w-4" /> Nueva Sucursal
        </Button>
      </div>

      <div className="border rounded-md">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>Nombre</TableHead>
              <TableHead>Coordenadas</TableHead>
              <TableHead>Acciones</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loading ? (
                <TableRow><TableCell colSpan={4} className="text-center">Cargando...</TableCell></TableRow>
            ) : branches.map((b) => (
                <TableRow key={b.id}>
                    <TableCell>{b.id}</TableCell>
                    <TableCell>{b.nombre}</TableCell>
                    <TableCell>{b.lat && b.lng ? `${b.lat.toFixed(4)}, ${b.lng.toFixed(4)}` : "N/A"}</TableCell>
                    <TableCell className="flex gap-2">
                         <Button variant="ghost" size="icon" onClick={() => handleEdit(b)}>
                             <Edit className="h-4 w-4" />
                         </Button>
                         <Button variant="ghost" size="icon" className="text-red-500" onClick={() => handleDelete(b.id)}>
                             <Trash2 className="h-4 w-4" />
                         </Button>
                    </TableCell>
                </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>

      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
          <DialogContent className="sm:max-w-[700px]">
              <DialogHeader>
                  <DialogTitle>{isEditing ? "Editar Sucursal" : "Nueva Sucursal"}</DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                  <div className="grid grid-cols-4 items-center gap-4">
                      <Label htmlFor="nombre" className="text-right">Nombre</Label>
                      <Input id="nombre" value={form.nombre} onChange={e => setForm({...form, nombre: e.target.value})} className="col-span-3"/>
                  </div>
                  
                  <div className="col-span-4">
                      <Label className="mb-2 block">Ubicación</Label>
                      <div className="relative z-0">
                        {isDialogOpen && (
                            <BranchMap 
                                lat={form.lat} 
                                lng={form.lng} 
                                onChange={(lat, lng) => setForm({...form, lat, lng})} 
                            />
                        )}
                      </div>
                      <p className="text-xs text-muted-foreground mt-1">
                          Haz click en el mapa para establecer la ubicación.
                      </p>
                  </div>
              </div>
              <DialogFooter>
                  <Button onClick={handleSave}>Guardar</Button>
              </DialogFooter>
          </DialogContent>
      </Dialog>
    </div>
  );
}
