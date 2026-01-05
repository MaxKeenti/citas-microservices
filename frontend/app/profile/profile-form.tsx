"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
  } from "@/components/ui/select"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { User } from "@/lib/definitions";

const formSchema = z.object({
  nombre: z.string().min(2, { message: "El nombre es requerido" }),
  primerApellido: z.string().min(2, { message: "El primer apellido es requerido" }),
  segundoApellido: z.string().optional(),
  fechaNacimiento: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, { message: "Formato inválido (YYYY-MM-DD)" }),
  idGenero: z.string().min(1, { message: "Seleccione un género" }),
});

export function ProfileForm({ user }: { user: User }) {
  const router = useRouter();
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nombre: user.nombre || "",
      primerApellido: user.primerApellido || "",
      segundoApellido: user.segundoApellido || "",
      // Assuming user.fechaNacimiento comes as [Y,M,D] array or string? 
      // Backend returns LocalDate as String usually "YYYY-MM-DD".
      fechaNacimiento: Array.isArray(user.fechaNacimiento) 
        ? `${user.fechaNacimiento[0]}-${String(user.fechaNacimiento[1]).padStart(2,'0')}-${String(user.fechaNacimiento[2]).padStart(2,'0')}`
        : (user.fechaNacimiento as string) || "",
      idGenero: user.idGenero ? String(user.idGenero) : "1",
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    setLoading(true);
    setError(null);
    setSuccess(null);

    try {
      const res = await fetch("/api/auth/profile", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
            id: user.id,
            ...values
        }),
      });

      const data = await res.json();

      if (!res.ok) {
        setError(data.message || "Error al actualizar perfil");
        setLoading(false);
        return;
      }

      setSuccess("Perfil actualizado correctamente");
      router.refresh(); // Refresh Server Components (Navbar name etc)
      
    } catch (err) {
      console.error(err);
      setError("Ocurrió un error inesperado");
    } finally {
        setLoading(false);
    }
  }

  return (
      <Card className="w-full max-w-lg mx-auto">
        <CardHeader>
          <CardTitle>Editar Perfil</CardTitle>
          <CardDescription>Actualiza tu información personal</CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                
              <div className="grid grid-cols-2 gap-4">
                <FormField
                    control={form.control}
                    name="nombre"
                    render={({ field }) => (
                    <FormItem>
                        <FormLabel>Nombre</FormLabel>
                        <FormControl>
                        <Input placeholder="Juan" {...field} />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="primerApellido"
                    render={({ field }) => (
                    <FormItem>
                        <FormLabel>Primer Apellido</FormLabel>
                        <FormControl>
                        <Input placeholder="Pérez" {...field} />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                    )}
                />
              </div>

              <div className="grid grid-cols-2 gap-4">
                <FormField
                    control={form.control}
                    name="segundoApellido"
                    render={({ field }) => (
                    <FormItem>
                        <FormLabel>Segundo Apellido</FormLabel>
                        <FormControl>
                        <Input placeholder="López" {...field} />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fechaNacimiento"
                    render={({ field }) => (
                    <FormItem>
                        <FormLabel>Fecha de Nacimiento</FormLabel>
                        <FormControl>
                        <Input type="date" {...field} />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                    )}
                />
              </div>

              <FormField
                control={form.control}
                name="idGenero"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Género</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Seleccione un género" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="1">Hombre</SelectItem>
                        <SelectItem value="2">Mujer</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              {error && (
                <div className="text-red-500 text-sm font-medium text-center">
                  {error}
                </div>
              )}
              {success && (
                <div className="text-green-500 text-sm font-medium text-center">
                  {success}
                </div>
              )}

              <Button type="submit" className="w-full" disabled={loading}>
                {loading ? "Guardando..." : "Guardar Cambios"}
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
  );
}
