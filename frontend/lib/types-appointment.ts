export interface AppointmentStatus {
    id: number;
    nombre: string;
}

export interface Cita {
    id: number;
    fechaHora: string;
    duracion: number;
    idServicio: number;
    idEmpleado?: number;
    idSucursal?: number;
    estado?: AppointmentStatus;
}
