-- Human Resources Service Initialization

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;

-- Establecimiento
CREATE TABLE public.tce01_establecimiento (
    id_establecimiento integer NOT NULL,
    tx_nombre character varying(100) NOT NULL
);
CREATE SEQUENCE public.tce01_establecimiento_id_establecimiento_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tce01_establecimiento_id_establecimiento_seq OWNED BY public.tce01_establecimiento.id_establecimiento;
ALTER TABLE ONLY public.tce01_establecimiento ALTER COLUMN id_establecimiento SET DEFAULT nextval('public.tce01_establecimiento_id_establecimiento_seq'::regclass);
ALTER TABLE ONLY public.tce01_establecimiento ADD CONSTRAINT tce01_establecimiento_pkey PRIMARY KEY (id_establecimiento);

-- Sucursal
CREATE TABLE public.tce02_sucursal (
    id_sucursal integer NOT NULL,
    fk_id_establecimiento integer NOT NULL,
    tx_nombre character varying(100) NOT NULL,
    gm_ubicacion public.geometry NOT NULL
);
CREATE SEQUENCE public.tce02_sucursal_id_sucursal_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tce02_sucursal_id_sucursal_seq OWNED BY public.tce02_sucursal.id_sucursal;
ALTER TABLE ONLY public.tce02_sucursal ALTER COLUMN id_sucursal SET DEFAULT nextval('public.tce02_sucursal_id_sucursal_seq'::regclass);
ALTER TABLE ONLY public.tce02_sucursal ADD CONSTRAINT tce02_sucursal_pkey PRIMARY KEY (id_sucursal);
ALTER TABLE ONLY public.tce02_sucursal ADD CONSTRAINT fktce02_sucu179423 FOREIGN KEY (fk_id_establecimiento) REFERENCES public.tce01_establecimiento(id_establecimiento);

-- Empleado
CREATE TABLE public.tce03_empleado (
    id_empleado integer NOT NULL,
    fk_id_sucursal integer NOT NULL
);
ALTER TABLE ONLY public.tce03_empleado ADD CONSTRAINT tce03_empleado_pkey PRIMARY KEY (id_empleado);
-- FK to Persona removed (Cross-bound)
ALTER TABLE ONLY public.tce03_empleado ADD CONSTRAINT fktce03_empl771275 FOREIGN KEY (fk_id_sucursal) REFERENCES public.tce02_sucursal(id_sucursal);

-- Dia Laboral
CREATE TABLE public.tce04_dia_laboral (
    id_dia integer NOT NULL,
    tx_nombre character varying(50) NOT NULL,
    tx_descripcion character varying(255) NOT NULL,
    st_activo integer NOT NULL
);
CREATE SEQUENCE public.tce04_dia_laboral_id_dia_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tce04_dia_laboral_id_dia_seq OWNED BY public.tce04_dia_laboral.id_dia;
ALTER TABLE ONLY public.tce04_dia_laboral ALTER COLUMN id_dia SET DEFAULT nextval('public.tce04_dia_laboral_id_dia_seq'::regclass);
ALTER TABLE ONLY public.tce04_dia_laboral ADD CONSTRAINT tce04_dia_laboral_pkey PRIMARY KEY (id_dia);
INSERT INTO public.tce04_dia_laboral VALUES (1, 'Lunes', 'Día laboral ordinario', 1);
INSERT INTO public.tce04_dia_laboral VALUES (2, 'Martes', 'Día laboral ordinario', 1);
INSERT INTO public.tce04_dia_laboral VALUES (3, 'Miércoles', 'Día laboral ordinario', 1);
INSERT INTO public.tce04_dia_laboral VALUES (4, 'Jueves', 'Día laboral ordinario', 1);
INSERT INTO public.tce04_dia_laboral VALUES (5, 'Viernes', 'Día laboral ordinario', 1);
INSERT INTO public.tce04_dia_laboral VALUES (6, 'Sábado', 'Día laboral ordinario', 1);
INSERT INTO public.tce04_dia_laboral VALUES (7, 'Domingo', 'Día laboral ordinario', 1);

-- Dia Descanso
CREATE TABLE public.tce05_dia_descanso (
    id_dia_descanso integer NOT NULL,
    fk_id_empleado integer NOT NULL,
    fh_descanso date NOT NULL
);
CREATE SEQUENCE public.tce05_dia_descanso_id_dia_descanso_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tce05_dia_descanso_id_dia_descanso_seq OWNED BY public.tce05_dia_descanso.id_dia_descanso;
ALTER TABLE ONLY public.tce05_dia_descanso ALTER COLUMN id_dia_descanso SET DEFAULT nextval('public.tce05_dia_descanso_id_dia_descanso_seq'::regclass);
ALTER TABLE ONLY public.tce05_dia_descanso ADD CONSTRAINT tce05_dia_descanso_pkey PRIMARY KEY (id_dia_descanso);
ALTER TABLE ONLY public.tce05_dia_descanso ADD CONSTRAINT fktce05_dia_792374 FOREIGN KEY (fk_id_empleado) REFERENCES public.tce03_empleado(id_empleado);

-- Horario
CREATE TABLE public.tce08_horario (
    id_horario integer NOT NULL,
    fk_id_sucursal integer NOT NULL,
    fk_id_dia integer NOT NULL,
    tm_inicio time without time zone NOT NULL,
    tm_fin time without time zone NOT NULL
);
CREATE SEQUENCE public.tce08_horario_id_horario_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tce08_horario_id_horario_seq OWNED BY public.tce08_horario.id_horario;
ALTER TABLE ONLY public.tce08_horario ALTER COLUMN id_horario SET DEFAULT nextval('public.tce08_horario_id_horario_seq'::regclass);
ALTER TABLE ONLY public.tce08_horario ADD CONSTRAINT tce08_horario_pkey PRIMARY KEY (id_horario);
ALTER TABLE ONLY public.tce08_horario ADD CONSTRAINT fktce08_hora383650 FOREIGN KEY (fk_id_sucursal) REFERENCES public.tce02_sucursal(id_sucursal);
ALTER TABLE ONLY public.tce08_horario ADD CONSTRAINT fktce08_hora893106 FOREIGN KEY (fk_id_dia) REFERENCES public.tce04_dia_laboral(id_dia);

-- Empleado Horario
CREATE TABLE public.tce06_empleado_horario (
    fk_id_horario integer NOT NULL,
    fk_id_persona integer NOT NULL
);
ALTER TABLE ONLY public.tce06_empleado_horario ADD CONSTRAINT tce06_empleado_horario_pkey PRIMARY KEY (fk_id_horario, fk_id_persona);
-- FK name implies it links to tce03_empleado (id_empleado), though column name keys off 'persona'.
-- tce03_empleado fits here.
ALTER TABLE ONLY public.tce06_empleado_horario ADD CONSTRAINT fktce06_empl130066 FOREIGN KEY (fk_id_persona) REFERENCES public.tce03_empleado(id_empleado);
ALTER TABLE ONLY public.tce06_empleado_horario ADD CONSTRAINT fktce06_empl703363 FOREIGN KEY (fk_id_horario) REFERENCES public.tce08_horario(id_horario);

-- Bloque Cita
CREATE TABLE public.tce07_bloque_cita (
    fk_id_sucursal integer NOT NULL,
    fk_id_cita integer,
    fh_inicio timestamp without time zone NOT NULL,
    fh_fin timestamp without time zone NOT NULL
);
-- No PK predefined in dump?
ALTER TABLE ONLY public.tce07_bloque_cita ADD CONSTRAINT fktce07_bloq429895 FOREIGN KEY (fk_id_sucursal) REFERENCES public.tce02_sucursal(id_sucursal);
-- FK to Cita removed (Cross-bound).
