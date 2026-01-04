-- Catalog Service Initialization

-- Servicio
CREATE TABLE public.cci01_servicio (
    id_servicio integer NOT NULL,
    tx_nombre character varying(50) NOT NULL,
    tx_descripcion character varying(255) NOT NULL,
    st_activo boolean NOT NULL,
    nu_duracion integer NOT NULL
);
CREATE SEQUENCE public.cci01_servicio_id_servicio_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.cci01_servicio_id_servicio_seq OWNED BY public.cci01_servicio.id_servicio;
ALTER TABLE ONLY public.cci01_servicio ALTER COLUMN id_servicio SET DEFAULT nextval('public.cci01_servicio_id_servicio_seq'::regclass);
ALTER TABLE ONLY public.cci01_servicio ADD CONSTRAINT cci01_servicio_pkey PRIMARY KEY (id_servicio);

-- Estado Lista Precio
CREATE TABLE public.tci01_estado_lista_precio (
    id_estado integer NOT NULL,
    tx_nombre character varying(50) NOT NULL
);
CREATE SEQUENCE public.tci01_estado_lista_precio_id_estado_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tci01_estado_lista_precio_id_estado_seq OWNED BY public.tci01_estado_lista_precio.id_estado;
ALTER TABLE ONLY public.tci01_estado_lista_precio ALTER COLUMN id_estado SET DEFAULT nextval('public.tci01_estado_lista_precio_id_estado_seq'::regclass);
ALTER TABLE ONLY public.tci01_estado_lista_precio ADD CONSTRAINT tci01_estado_lista_precio_pkey PRIMARY KEY (id_estado);

-- Lista Precio
CREATE TABLE public.tci03_lista_precio (
    id_lista_precio integer NOT NULL,
    fk_id_estado integer NOT NULL,
    tx_nombre character varying(50) NOT NULL,
    fh_inicio timestamp without time zone NOT NULL,
    fh_fin timestamp without time zone,
    st_activo boolean NOT NULL
);
CREATE SEQUENCE public.tci03_lista_precio_id_lista_precio_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tci03_lista_precio_id_lista_precio_seq OWNED BY public.tci03_lista_precio.id_lista_precio;
ALTER TABLE ONLY public.tci03_lista_precio ALTER COLUMN id_lista_precio SET DEFAULT nextval('public.tci03_lista_precio_id_lista_precio_seq'::regclass);
ALTER TABLE ONLY public.tci03_lista_precio ADD CONSTRAINT tci03_lista_precio_pkey PRIMARY KEY (id_lista_precio);
ALTER TABLE ONLY public.tci03_lista_precio ADD CONSTRAINT fktci03_list512910 FOREIGN KEY (fk_id_estado) REFERENCES public.tci01_estado_lista_precio(id_estado);

-- Servicio Lista Precio
CREATE TABLE public.tci02_servicio_lista_precio (
    id_servicio_lista_precio integer NOT NULL,
    fk_id_servicio integer NOT NULL,
    fk_id_lista_precio integer NOT NULL,
    nu_precio integer NOT NULL
);
CREATE SEQUENCE public.tci02_servicio_lista_precio_id_servicio_lista_precio_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tci02_servicio_lista_precio_id_servicio_lista_precio_seq OWNED BY public.tci02_servicio_lista_precio.id_servicio_lista_precio;
ALTER TABLE ONLY public.tci02_servicio_lista_precio ALTER COLUMN id_servicio_lista_precio SET DEFAULT nextval('public.tci02_servicio_lista_precio_id_servicio_lista_precio_seq'::regclass);
ALTER TABLE ONLY public.tci02_servicio_lista_precio ADD CONSTRAINT tci02_servicio_lista_precio_pkey PRIMARY KEY (id_servicio_lista_precio);
ALTER TABLE ONLY public.tci02_servicio_lista_precio ADD CONSTRAINT tci02_servicio_lista_precio_unique UNIQUE (fk_id_servicio, fk_id_lista_precio);
ALTER TABLE ONLY public.tci02_servicio_lista_precio ADD CONSTRAINT fktci02_serv131929 FOREIGN KEY (fk_id_servicio) REFERENCES public.cci01_servicio(id_servicio);
ALTER TABLE ONLY public.tci02_servicio_lista_precio ADD CONSTRAINT fktci02_serv753638 FOREIGN KEY (fk_id_lista_precio) REFERENCES public.tci03_lista_precio(id_lista_precio);
