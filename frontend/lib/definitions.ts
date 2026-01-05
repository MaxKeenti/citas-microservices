export type User = {
  id: number;
  nombre: string;
  primerApellido: string;
  segundoApellido?: string;
  roles: string[];
};

export type SessionPayload = {
  user: User;
  isLoggedIn: boolean;
};

export type LoginFormState = {
  errors?: {
    username?: string[];
    password?: string[];
    general?: string[];
  };
  message?: string;
};

export type Service = {
  id: number;
  nombre: string;
  descripcion: string;
  duracion: string; // Duration string like "PT30M"
};
