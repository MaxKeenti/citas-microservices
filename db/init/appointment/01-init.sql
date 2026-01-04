-- Appointment Service Initialization

-- Cita
CREATE TABLE public.tci05_cita (
    id_cita integer NOT NULL,
    fk_id_persona integer NOT NULL,
    fk_id_servicio integer NOT NULL,
    fk_id_lista_precio integer NOT NULL,
    fk_id_sucursal integer NOT NULL,
    fk_id_empleado integer NOT NULL,
    fh_cita timestamp without time zone,
    nu_duracion integer
);
CREATE SEQUENCE public.tci05_cita_id_cita_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tci05_cita_id_cita_seq OWNED BY public.tci05_cita.id_cita;
ALTER TABLE ONLY public.tci05_cita ALTER COLUMN id_cita SET DEFAULT nextval('public.tci05_cita_id_cita_seq'::regclass);
ALTER TABLE ONLY public.tci05_cita ADD CONSTRAINT tci05_cita_pkey PRIMARY KEY (id_cita);

-- FKs removed as they cross service boundaries:
-- - fk_id_persona (Access Control)
-- - fk_id_servicio, fk_id_lista_precio (Catalog)
-- - fk_id_sucursal (HResources)
-- - fk_id_empleado (HResources)
