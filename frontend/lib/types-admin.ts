export type Employee = {
    id: number;
    nombre: string;
    primerApellido: string;
    segundoApellido: string;
    sucursal: {
        id: number;
        nombre: string;
    }
};

export type Branch = {
    id: number;
    nombre: string;
};
