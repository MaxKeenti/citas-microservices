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

-- Estado Cita
CREATE TABLE public.tci06_estado_cita (
    id_estado integer NOT NULL,
    tx_nombre character varying(50) NOT NULL
);
CREATE SEQUENCE public.tci06_estado_cita_id_estado_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.tci06_estado_cita_id_estado_seq OWNED BY public.tci06_estado_cita.id_estado;
ALTER TABLE ONLY public.tci06_estado_cita ALTER COLUMN id_estado SET DEFAULT nextval('public.tci06_estado_cita_id_estado_seq'::regclass);
ALTER TABLE ONLY public.tci06_estado_cita ADD CONSTRAINT tci06_estado_cita_pkey PRIMARY KEY (id_estado);

-- Add column and FK to Cita
-- Defaulting to 1 (Agendada) for existing records if any (though this is init script)
ALTER TABLE public.tci05_cita ADD COLUMN fk_id_estado integer NOT NULL DEFAULT 1;
ALTER TABLE ONLY public.tci05_cita ADD CONSTRAINT fktci05_cita_estado FOREIGN KEY (fk_id_estado) REFERENCES public.tci06_estado_cita(id_estado);

-- Seed Data
INSERT INTO public.tci06_estado_cita (id_estado, tx_nombre) VALUES
(1, 'Agendada'),
(2, 'Confirmada'),
(3, 'Cancelada'),
(4, 'Completada'),
(5, 'No Asistió');


-- Seed Data - Appointments
-- Ensure Person IDs (1001, 1002) exist in Access Control Service db.
-- Ensure Service IDs (1, 2) exist in Catalog.
-- Ensure Price List IDs (1, 2) exist in Catalog.
-- Ensure Branch IDs (1) exist in HResources.
-- Ensure Employee IDs (1) exist in HResources.

-- We cannot strictly enforce foreign keys across services in this monolithic init script if we were truly distributed, 
-- but since we are mocking/seeding, we just insert raw IDs.

INSERT INTO public.tci05_cita (id_cita, fk_id_persona, fk_id_servicio, fk_id_lista_precio, fk_id_sucursal, fk_id_empleado, fh_cita, nu_duracion, fk_id_estado) VALUES
(1, 1001, 1, 1, 1, 1, '2026-06-15 10:00:00', 30, 1), -- Agendada
(2, 1002, 2, 1, 1, 1, '2026-06-15 11:00:00', 30, 2), -- Confirmada
(3, 1001, 3, 1, 1, 1, '2026-06-16 12:00:00', 60, 4); -- Completada

ALTER SEQUENCE public.tci05_cita_id_cita_seq RESTART WITH 4;

-- FKs removed as they cross service boundaries:
-- - fk_id_persona (Access Control)
-- - fk_id_servicio, fk_id_lista_precio (Catalog)
-- - fk_id_sucursal (HResources)
-- - fk_id_empleado (HResources)
