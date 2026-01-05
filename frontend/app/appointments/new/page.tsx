"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Service, Employee } from "@/lib/definitions";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

// Helper to generate time slots
function generateSlots(startHour: number, endHour: number, durationMinutes: number) {
    const slots = [];
    let current = new Date();
    current.setHours(startHour, 0, 0, 0);
    const end = new Date();
    end.setHours(endHour, 0, 0, 0);

    while (current < end) {
        const timeString = current.toTimeString().substring(0, 5); // HH:MM
        slots.push(timeString);
        current.setMinutes(current.getMinutes() + durationMinutes);
    }
    return slots;
}

export default function NewAppointmentPage() {
    const router = useRouter();
    const [step, setStep] = useState(1);
    
    // Data
    const [services, setServices] = useState<Service[]>([]);
    const [employees, setEmployees] = useState<Employee[]>([]);
    
    // Selections
    const [selectedService, setSelectedService] = useState<Service | null>(null);
    const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(null);
    const [selectedDate, setSelectedDate] = useState<string>("");
    const [selectedTime, setSelectedTime] = useState<string>("");
    
    // Availability
    const [availableSlots, setAvailableSlots] = useState<string[]>([]);
    const [loadingSlots, setLoadingSlots] = useState(false);
    const [loading, setLoading] = useState(false);

    // Fetch Services on Mount
    useEffect(() => {
        fetch("/api/services")
            .then(res => res.json())
            .then(data => setServices(Array.isArray(data) ? data : []));
    }, []);

    // Fetch Employees when Service Selected (Mocking filtering for now, or fetch all)
    // Ideally we filter employees that can perform the service.
    useEffect(() => {
        if (step === 2) {
             fetch("/api/employees")
                .then(res => res.json())
                .then(data => setEmployees(Array.isArray(data) ? data : []));
        }
    }, [step]);

    // Calculate Availability
    useEffect(() => {
        if (selectedDate && selectedEmployee && selectedService) {
            setLoadingSlots(true);
            // Fetch busy slots
            fetch(`/api/availability?date=${selectedDate}&employeeId=${selectedEmployee.id}&serviceDuration=${selectedService.duracion || 30}`)
                .then(res => res.json())
                .then(slots => {
                    // slots is Array of strings ["HH:MM:SS", ...]
                    // We just need to format them to HH:MM
                    if (Array.isArray(slots)) {
                        const formatted = slots.map((t: string) => t.substring(0, 5));
                        setAvailableSlots(formatted);
                    } else {
                        setAvailableSlots([]);
                    }
                    setLoadingSlots(false);
                })
                .catch(err => {
                    console.error("Failed to fetch availability", err);
                    setLoadingSlots(false);
                });
        }
    }, [selectedDate, selectedEmployee, selectedService]);

    const handleConfirm = async () => {
        if (!selectedService || !selectedEmployee || !selectedDate || !selectedTime) return;
        setLoading(true);

        const fechaHora = `${selectedDate}T${selectedTime}:00`;

        try {
            const res = await fetch("/api/appointments", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    idServicio: selectedService.id,
                    idEmpleado: selectedEmployee.id,
                    fechaHora: fechaHora,
                    duracion: selectedService.duracion || 30,
                    // Additional fields (idSucursal, idListaPrecio) handled by API route defaults
                })
            });

            if (res.ok) {
                router.push("/appointments");
                router.refresh();
            } else {
                alert("Error booking appointment");
            }
        } catch (e) {
            console.error(e);
            alert("Error");
        }
        setLoading(false);
    };

    // Render Steps
    return (
        <div className="container mx-auto p-6 max-w-2xl">
            <h1 className="text-3xl font-bold mb-6">Nueva Cita</h1>
            
            {/* Progress Indicator */}
            <div className="flex justify-between mb-8 text-sm text-muted-foreground">
                <span className={step === 1 ? "font-bold text-primary" : ""}>1. Servicio</span>
                <span className={step === 2 ? "font-bold text-primary" : ""}>2. Profesional</span>
                <span className={step === 3 ? "font-bold text-primary" : ""}>3. Fecha y Hora</span>
                <span className={step === 4 ? "font-bold text-primary" : ""}>4. Confirmar</span>
            </div>

            <Card>
                <CardHeader>
                    <CardTitle>
                        {step === 1 && "Selecciona un Servicio"}
                        {step === 2 && "Selecciona un Profesional"}
                        {step === 3 && "Selecciona Fecha y Hora"}
                        {step === 4 && "Confirmar Reserva"}
                    </CardTitle>
                    {step === 2 && <CardDescription>Servicio: {selectedService?.nombre}</CardDescription>}
                    {step === 3 && <CardDescription>{selectedService?.nombre} con {selectedEmployee?.nombre} {selectedEmployee?.primerApellido}</CardDescription>}
                </CardHeader>

                <CardContent>
                    {step === 1 && (
                        <div className="grid gap-2">
                            {services.map(s => (
                                <Button 
                                    key={s.id} 
                                    variant="outline" 
                                    className="justify-start h-auto py-3 px-4"
                                    onClick={() => { setSelectedService(s); setStep(2); }}
                                >
                                    <div className="text-left">
                                        <div className="font-semibold">{s.nombre}</div>
                                        <div className="text-xs text-muted-foreground">{s.duracion} mins</div>
                                    </div>
                                </Button>
                            ))}
                        </div>
                    )}

                    {step === 2 && (
                        <div className="grid gap-2">
                            {employees.map(e => (
                                <Button 
                                    key={e.id} 
                                    variant="outline" 
                                    className="justify-start py-3"
                                    onClick={() => { setSelectedEmployee(e); setStep(3); }}
                                >
                                    {e.nombre} {e.primerApellido}
                                </Button>
                            ))}
                        </div>
                    )}

                    {step === 3 && (
                        <div className="space-y-4">
                           <div className="grid gap-2">
                                <Label>Fecha</Label>
                                <Input 
                                    type="date" 
                                    min={new Date().toISOString().split("T")[0]}
                                    value={selectedDate}
                                    onChange={(e) => { setSelectedDate(e.target.value); setSelectedTime(""); }}
                                />
                           </div>

                           {selectedDate && (
                               <div>
                                   <Label>Horarios Disponibles</Label>
                                   {loadingSlots ? (
                                       <div className="py-4 text-sm text-center">Buscando horarios...</div>
                                   ) : (
                                       <div className="grid grid-cols-4 gap-2 mt-2">
                                           {availableSlots.length > 0 ? availableSlots.map(time => (
                                               <Button 
                                                    key={time}
                                                    variant={selectedTime === time ? "default" : "outline"}
                                                    onClick={() => setSelectedTime(time)}
                                                    size="sm"
                                               >
                                                   {time}
                                               </Button>
                                           )) : (
                                               <div className="col-span-4 text-sm text-red-500">No hay horarios disponibles.</div>
                                           )}
                                       </div>
                                   )}
                               </div>
                           )}
                        </div>
                    )}

                    {step === 4 && (
                        <div className="space-y-4">
                            <div className="bg-muted p-4 rounded-md">
                                <p><strong>Servicio:</strong> {selectedService?.nombre}</p>
                                <p><strong>Profesional:</strong> {selectedEmployee?.nombre} {selectedEmployee?.primerApellido}</p>
                                <p><strong>Fecha:</strong> {selectedDate}</p>
                                <p><strong>Hora:</strong> {selectedTime}</p>
                                <p><strong>Duración:</strong> {selectedService?.duracion} min</p>
                            </div>
                        </div>
                    )}

                </CardContent>
                <CardFooter className="flex justify-between">
                    {step > 1 && (
                        <Button variant="ghost" onClick={() => setStep(step - 1)}>Atrás</Button>
                    )}
                    {step === 3 && (
                        <Button disabled={!selectedTime} onClick={() => setStep(4)}>Continuar</Button>
                    )}
                    {step === 4 && (
                        <Button onClick={handleConfirm} disabled={loading}>
                            {loading ? "Reservando..." : "Confirmar Cita"}
                        </Button>
                    )}
                </CardFooter>
            </Card>
        </div>
    );
}
