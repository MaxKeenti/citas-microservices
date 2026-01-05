-- Initial Data for Catalog Service
INSERT INTO public.cci01_servicio (id_servicio, tx_nombre, tx_descripcion, st_activo, nu_duracion) VALUES 
(1, 'Corte de Cabello', 'Corte de cabello clásico o moderno a tijera y máquina.', true, 30),
(2, 'Afeitado de Barba', 'Afeitado tradicional con navaja y toalla caliente.', true, 30),
(3, 'Corte y Barba', 'Servicio completo de corte de cabello y arreglo de barba.', true, 60),
(4, 'Tinte de Cabello', 'Coloración de cabello para cubrir canas o cambio de look.', true, 90),
(5, 'Mascarilla Facial', 'Limpieza y exfoliación facial profunda.', true, 45);

ALTER SEQUENCE public.cci01_servicio_id_servicio_seq RESTART WITH 6;
