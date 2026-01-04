-- Access Control Service Initialization

-- Genero
CREATE TABLE public.cca01_genero (
    id_genero integer NOT NULL,
    tx_nombre character varying(50) NOT NULL,
    tx_descripcion character varying(255) NOT NULL,
    st_activo boolean NOT NULL
);
ALTER TABLE ONLY public.cca01_genero ADD CONSTRAINT cca01_genero_pkey PRIMARY KEY (id_genero);
INSERT INTO public.cca01_genero VALUES (1, 'Hombre', 'Hombre', true);
INSERT INTO public.cca01_genero VALUES (2, 'Mujer', 'Mujer', true);

-- Rol
CREATE TABLE public.cca02_rol (
    id_rol integer NOT NULL,
    tx_nombre character varying(50) NOT NULL,
    tx_descripcion character varying(255) NOT NULL,
    st_activo boolean NOT NULL
);
ALTER TABLE ONLY public.cca02_rol ADD CONSTRAINT cca02_rol_pkey PRIMARY KEY (id_rol);
INSERT INTO public.cca02_rol VALUES (1, 'Admin', 'Admin', true);
INSERT INTO public.cca02_rol VALUES (2, 'Empleado', 'Empleado', true);
INSERT INTO public.cca02_rol VALUES (3, 'Cliente', 'Cliente', true);

-- Persona
CREATE TABLE public.tca01_persona (
    id_persona integer NOT NULL,
    fk_id_genero integer NOT NULL,
    tx_nombre character varying(100) NOT NULL,
    tx_primer_apellido character varying(100) NOT NULL,
    tx_segundo_apellido character varying(100),
    fh_nacimiento date NOT NULL
);
CREATE SEQUENCE public.tca01_persona_id_persona_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tca01_persona_id_persona_seq OWNED BY public.tca01_persona.id_persona;
ALTER TABLE ONLY public.tca01_persona ALTER COLUMN id_persona SET DEFAULT nextval('public.tca01_persona_id_persona_seq'::regclass);
ALTER TABLE ONLY public.tca01_persona ADD CONSTRAINT tca01_persona_pkey PRIMARY KEY (id_persona);
ALTER TABLE ONLY public.tca01_persona ADD CONSTRAINT fktca01_pers852780 FOREIGN KEY (fk_id_genero) REFERENCES public.cca01_genero(id_genero);

-- Usuario
CREATE TABLE public.tca02_usuario (
    id_usuario integer NOT NULL,
    fk_id_rol integer NOT NULL,
    tx_login character varying(100) NOT NULL,
    tx_password character varying(255) NOT NULL,
    st_activo boolean NOT NULL
);
ALTER TABLE ONLY public.tca02_usuario ADD CONSTRAINT tca02_usuario_pkey PRIMARY KEY (id_usuario);
ALTER TABLE ONLY public.tca02_usuario ADD CONSTRAINT uk_tx_login UNIQUE (tx_login);
ALTER TABLE ONLY public.tca02_usuario ADD CONSTRAINT fktca02_usua571784 FOREIGN KEY (fk_id_rol) REFERENCES public.cca02_rol(id_rol);
ALTER TABLE ONLY public.tca02_usuario ADD CONSTRAINT fktca02_usua862702 FOREIGN KEY (id_usuario) REFERENCES public.tca01_persona(id_persona);
