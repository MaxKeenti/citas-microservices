"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Plus, Edit, Trash2 } from "lucide-react";
import Link from "next/link";
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
  DialogDescription,
  DialogFooter,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";
import { PriceList } from "@/lib/types-admin";

export default function PriceListsPage() {
  const [lists, setLists] = useState<PriceList[]>([]);
  const [loading, setLoading] = useState(true);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [form, setForm] = useState<Partial<PriceList>>({ nombre: "", activo: true });

  const fetchLists = async () => {
    try {
      const res = await fetch("/api/admin/price-lists");
      if (res.ok) setLists(await res.json());
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchLists(); }, []);

  const handleCreate = async () => {
    await fetch("/api/admin/price-lists", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form)
    });
    setIsDialogOpen(false);
    fetchLists();
  };

  const handleDelete = async (id: number) => {
    if (!confirm("Are you sure?")) return;
    await fetch(`/api/admin/price-lists/${id}`, { method: "DELETE" });
    fetchLists();
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-bold tracking-tight">Listas de Precios</h2>
          <p className="text-muted-foreground">Gestiona precios base y promociones.</p>
        </div>
        <Button onClick={() => { setForm({ nombre: "", activo: true }); setIsDialogOpen(true); }}>
          <Plus className="mr-2 h-4 w-4" /> Nueva Lista
        </Button>
      </div>

      <div className="border rounded-md">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>ID</TableHead>
              <TableHead>Nombre</TableHead>
              <TableHead>Estado</TableHead>
              <TableHead>Acciones</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loading ? <TableRow><TableCell colSpan={4}>Cargando...</TableCell></TableRow> : lists.map((l) => (
              <TableRow key={l.id}>
                <TableCell>{l.id}</TableCell>
                <TableCell>{l.nombre}</TableCell>
                <TableCell>
                  <span className={`px-2 py-1 rounded text-xs ${l.activo ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'}`}>
                    {l.activo ? "Activo" : "Inactivo"}
                  </span>
                </TableCell>
                <TableCell className="flex gap-2">
                  <Button variant="outline" size="sm" asChild>
                    <Link href={`/admin/price-lists/${l.id}`}>
                      <Edit className="h-4 w-4 mr-1" /> Items
                    </Link>
                  </Button>
                  <Button variant="ghost" size="icon" className="text-red-500" onClick={() => handleDelete(l.id)}>
                    <Trash2 className="h-4 w-4" />
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>

      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
        <DialogContent>
          <DialogHeader><DialogTitle>Nueva Lista de Precios</DialogTitle></DialogHeader>
          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="nombre" className="text-right">Nombre</Label>
              <Input id="nombre" value={form.nombre} onChange={e => setForm({ ...form, nombre: e.target.value })} className="col-span-3" />
            </div>
            <div className="flex items-center space-x-2">
              <Switch id="activo" checked={form.activo} onCheckedChange={(c) => setForm({ ...form, activo: c })} />
              <Label htmlFor="activo">Activa</Label>
            </div>
          </div>
          <DialogFooter>
            <Button onClick={handleCreate}>Guardar</Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}
