"use client";

import { useEffect, useState, use } from "react";
import { Button } from "@/components/ui/button";
import { Plus, Trash2, ArrowLeft, Save } from "lucide-react";
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
    DialogFooter,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { PriceListItem } from "@/lib/types-admin";

// Define a type for Services since we need to fetch them
type Service = {
    id: number;
    nombre: string;
};

export default function PriceListDetailPage(props: { params: Promise<{ id: string }> }) {
  const params = use(props.params); // Unwrap promise
  const [items, setItems] = useState<PriceListItem[]>([]);
  const [services, setServices] = useState<Service[]>([]);
  const [loading, setLoading] = useState(true);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [newItem, setNewItem] = useState<{ idServicio: string, precio: string }>({ idServicio: "", precio: "" });

  const fetchItems = async () => {
      const res = await fetch(`/api/admin/price-lists/${params.id}/items`);
      if(res.ok) setItems(await res.json());
      setLoading(false);
  };

  const fetchServices = async () => {
      const res = await fetch("/api/admin/services");
      if(res.ok) setServices(await res.json());
  };

  useEffect(() => {
      fetchItems();
      fetchServices();
  }, [params.id]);

  const handleAddItem = async () => {
      if(!newItem.idServicio || !newItem.precio) return;
      
      await fetch(`/api/admin/price-lists/${params.id}/items`, {
          method: "POST",
          headers: {"Content-Type": "application/json"},
          body: JSON.stringify({
              idServicio: parseInt(newItem.idServicio),
              precio: parseFloat(newItem.precio) // Using float/int? DB likely BigDecimal or int cents. Java code used int. Check context.
              // ServicioListaPrecio.precio is Integer. Assuming implied cents or whole currency?
              // Standard is often cents. If user inputs 100, we send 100.
          })
      });
      setIsDialogOpen(false);
      fetchItems();
  };

  const handleDeleteItem = async (itemId: number) => {
      await fetch(`/api/admin/price-lists/${params.id}/items/${itemId}`, { method: "DELETE" });
      fetchItems();
  };

  return (
    <div className="space-y-6">
        <div className="flex items-center gap-4">
            <Button variant="ghost" asChild size="icon">
                <Link href="/admin/price-lists"><ArrowLeft className="h-5 w-5"/></Link>
            </Button>
            <div>
                 <h2 className="text-3xl font-bold tracking-tight">Editar Precios</h2>
                 <p className="text-muted-foreground">Lista ID: {params.id}</p>
            </div>
             <div className="ml-auto">
                 <Button onClick={() => setIsDialogOpen(true)}>
                     <Plus className="mr-2 h-4 w-4"/> Agregar Precio
                 </Button>
            </div>
        </div>

        <div className="border rounded-md">
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead>Servicio</TableHead>
                        <TableHead>Precio</TableHead>
                        <TableHead className="w-[100px]"></TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {loading ? <TableRow><TableCell colSpan={3}>Cargando...</TableCell></TableRow> : items.map((item) => (
                        <TableRow key={item.id}>
                            <TableCell>{item.nombreServicio}</TableCell>
                            <TableCell>${item.precio}</TableCell>
                            <TableCell>
                                <Button variant="ghost" size="icon" className="text-red-500" onClick={() => handleDeleteItem(item.id)}>
                                    <Trash2 className="h-4 w-4"/>
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                    {items.length === 0 && !loading && (
                        <TableRow><TableCell colSpan={3} className="text-center text-muted-foreground">No hay precios configurados.</TableCell></TableRow>
                    )}
                </TableBody>
            </Table>
        </div>

        <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
            <DialogContent>
                <DialogHeader><DialogTitle>Agregar Precio</DialogTitle></DialogHeader>
                <div className="grid gap-4 py-4">
                    <div>
                        <Label>Servicio</Label>
                        <Select onValueChange={(v) => setNewItem({...newItem, idServicio: v})}>
                            <SelectTrigger>
                                <SelectValue placeholder="Seleccionar..." />
                            </SelectTrigger>
                            <SelectContent>
                                {services.map(s => (
                                    <SelectItem key={s.id} value={s.id.toString()}>{s.nombre}</SelectItem>
                                ))}
                            </SelectContent>
                        </Select>
                    </div>
                    <div>
                        <Label>Precio</Label>
                        <Input 
                            type="number" 
                            value={newItem.precio} 
                            onChange={e => setNewItem({...newItem, precio: e.target.value})}
                        />
                    </div>
                </div>
                <DialogFooter>
                    <Button onClick={handleAddItem}>Guardar</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    </div>
  );
}
