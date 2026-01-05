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

export type PriceList = {
    id: number;
    nombre: string;
    inicio?: string;
    fin?: string;
    activo: boolean;
};

export type PriceListItem = {
    id: number;
    idServicio: number;
    nombreServicio: string;
    precio: number;
};
