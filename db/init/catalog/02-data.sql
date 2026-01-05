-- Initial Data for Catalog Service
INSERT INTO public.cci01_servicio (id_servicio, tx_nombre, tx_descripcion, st_activo, nu_duracion) VALUES 
(1, 'Corte de Cabello', 'Corte de cabello clásico o moderno a tijera y máquina.', true, 30),
(2, 'Afeitado de Barba', 'Afeitado tradicional con navaja y toalla caliente.', true, 30),
(3, 'Corte y Barba', 'Servicio completo de corte de cabello y arreglo de barba.', true, 60),
(4, 'Tinte de Cabello', 'Coloración de cabello para cubrir canas o cambio de look.', true, 90),
(5, 'Mascarilla Facial', 'Limpieza y exfoliación facial profunda.', true, 45);

INSERT INTO public.tci01_estado_lista_precio (id_estado, tx_nombre) VALUES
(1, 'Activo'),
(2, 'Inactivo');

INSERT INTO public.tci03_lista_precio (id_lista_precio, tx_nombre, fk_id_estado, st_activo, fh_inicio, fh_fin) VALUES
(1, 'Lista General 2025', 1, true, '2025-01-01 00:00:00', '2025-12-31 23:59:59');

INSERT INTO public.tci02_servicio_lista_precio (fk_id_servicio, fk_id_lista_precio, nu_precio) VALUES
(1, 1, 250),
(2, 1, 150),
(3, 1, 350),
(4, 1, 600),
(5, 1, 300);

ALTER SEQUENCE public.cci01_servicio_id_servicio_seq RESTART WITH 6;
