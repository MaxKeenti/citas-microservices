"use client";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useState, useEffect } from "react";
// Import Branch type locally or from definition if available
type Branch = { id: number; nombre: string };

interface EmployeeDialogProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onSave: (data: any) => Promise<void>;
  branches: Branch[];
}

export function EmployeeDialog({ open, onOpenChange, onSave, branches }: EmployeeDialogProps) {
  const [formData, setFormData] = useState({
    nombre: "",
    primerApellido: "",
    segundoApellido: "",
    username: "",
    password: "",
    fechaNacimiento: "1990-01-01",
    idGenero: 1, // 1=Masculino default
    sucursalId: "",
  });
  const [loading, setLoading] = useState(false);

  // Reset form when opening
  useEffect(() => {
    if (open) {
         setFormData({
            nombre: "",
            primerApellido: "",
            segundoApellido: "",
            username: "",
            password: "",
            fechaNacimiento: "1990-01-01",
            idGenero: 1,
            sucursalId: branches.length > 0 ? branches[0].id.toString() : "",
          });
    }
  }, [open, branches]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      await onSave({
        ...formData,
        sucursalId: parseInt(formData.sucursalId),
        idGenero: Number(formData.idGenero)
      });
      onOpenChange(false);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px] overflow-y-auto max-h-[90vh]">
        <DialogHeader>
          <DialogTitle>Registrar Empleado</DialogTitle>
          <DialogDescription>
            Crea un nuevo usuario con rol de Empleado y asígnalo a una sucursal.
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit}>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="nombre" className="text-right">Nombre</Label>
              <Input id="nombre" value={formData.nombre} onChange={(e) => setFormData({...formData, nombre: e.target.value})} className="col-span-3" required />
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="primerApellido" className="text-right">1er Apellido</Label>
              <Input id="primerApellido" value={formData.primerApellido} onChange={(e) => setFormData({...formData, primerApellido: e.target.value})} className="col-span-3" required />
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="segundoApellido" className="text-right">2do Apellido</Label>
              <Input id="segundoApellido" value={formData.segundoApellido} onChange={(e) => setFormData({...formData, segundoApellido: e.target.value})} className="col-span-3" />
            </div>
             
             {/* Login Info */}
             <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="username" className="text-right">Usuario/Email</Label>
              <Input id="username" value={formData.username} onChange={(e) => setFormData({...formData, username: e.target.value})} className="col-span-3" required />
            </div>
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="password" className="text-right">Contraseña</Label>
              <Input id="password" type="password" value={formData.password} onChange={(e) => setFormData({...formData, password: e.target.value})} className="col-span-3" required />
            </div>

            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="sucursal" className="text-right">Sucursal</Label>
              <Select onValueChange={(val) => setFormData({...formData, sucursalId: val})} value={formData.sucursalId}>
                <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Selecciona sucursal" />
                </SelectTrigger>
                <SelectContent>
                    {branches.map((b) => (
                        <SelectItem key={b.id} value={b.id.toString()}>{b.nombre}</SelectItem>
                    ))}
                </SelectContent>
              </Select>
            </div>
             <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="genero" className="text-right">Género</Label>
               <Select onValueChange={(val) => setFormData({...formData, idGenero: parseInt(val)})} value={formData.idGenero.toString()}>
                <SelectTrigger className="col-span-3">
                    <SelectValue placeholder="Selecciona" />
                </SelectTrigger>
                <SelectContent>
                    <SelectItem value="1">Masculino</SelectItem>
                    <SelectItem value="2">Femenino</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>
          <DialogFooter>
            <Button type="submit" disabled={loading}>
              {loading ? "Guardando..." : "Guardar"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
