-- 03-seed-data.sql
-- Initial seed data for the application

-- 1. Establecimiento
INSERT INTO public.tce01_establecimiento (id_establecimiento, tx_nombre)
VALUES (1, 'Barbería UPIICSA');

SELECT setval('public.tce01_establecimiento_id_establecimiento_seq', (SELECT MAX(id_establecimiento) FROM public.tce01_establecimiento));

-- 2. Sucursal
-- Using a default point in Mexico City (approximate central location: -99.1332, 19.4326)
INSERT INTO public.tce02_sucursal (id_sucursal, fk_id_establecimiento, tx_nombre, gm_ubicacion)
VALUES (1, 1, 'Sucursal Principal', ST_SetSRID(ST_MakePoint(-99.091390, 19.396110), 4326)); -- Coordinates for UPIICSA

SELECT setval('public.tce02_sucursal_id_sucursal_seq', (SELECT MAX(id_sucursal) FROM public.tce02_sucursal));

-- 3. Estado Lista Precio
INSERT INTO public.tci01_estado_lista_precio (id_estado, tx_nombre)
VALUES (1, 'Activa'), (2, 'Inactiva');

SELECT setval('public.tci01_estado_lista_precio_id_estado_seq', (SELECT MAX(id_estado) FROM public.tci01_estado_lista_precio));

-- 4. Servicios
INSERT INTO public.cci01_servicio (id_servicio, tx_nombre, tx_descripcion, st_activo, nu_duracion)
VALUES 
(1, 'Corte de Cabello', 'Corte de cabello estilo clásico o moderno', true, 30),
(2, 'Afeitado de Barba', 'Afeitado completo o perfilado de barba', true, 20),
(3, 'Paquete Completo', 'Corte de cabello y afeitado de barba', true, 50);

SELECT setval('public.cci01_servicio_id_servicio_seq', (SELECT MAX(id_servicio) FROM public.cci01_servicio));

-- 5. Lista Precio
INSERT INTO public.tci03_lista_precio (id_lista_precio, fk_id_estado, tx_nombre, fh_inicio, fh_fin, st_activo)
VALUES (1, 1, 'Lista General 2026', NOW(), NULL, true);

SELECT setval('public.tci03_lista_precio_id_lista_precio_seq', (SELECT MAX(id_lista_precio) FROM public.tci03_lista_precio));

-- 6. Servicio Lista Precio
INSERT INTO public.tci02_servicio_lista_precio (id_servicio_lista_precio, fk_id_servicio, fk_id_lista_precio, nu_precio)
VALUES 
(1, 1, 1, 150), -- Corte: $150
(2, 2, 1, 100), -- Barba: $100
(3, 3, 1, 220); -- Paquete: $220

SELECT setval('public.tci02_servicio_lista_precio_id_servicio_lista_precio_seq', (SELECT MAX(id_servicio_lista_precio) FROM public.tci02_servicio_lista_precio));

-- 7. Mock Employee (Juan Pérez)
-- 7.1 Persona
INSERT INTO public.tca01_persona (id_persona, fk_id_genero, tx_nombre, tx_primer_apellido, tx_segundo_apellido, fh_nacimiento)
VALUES (2, 1, 'Juan', 'Pérez', 'López', '1995-05-15');

SELECT setval('public.tca01_persona_id_persona_seq', (SELECT MAX(id_persona) FROM public.tca01_persona));

-- 7.2 Usuario (Role 2 = Empleado, using same hash as admin for 'potaxie')
INSERT INTO public.tca02_usuario (id_usuario, fk_id_rol, tx_login, tx_password, st_activo)
VALUES (2, 2, 'empleado@example.com', 'f89Lo5HEh4Tt3lmYidbj8eR6J9s27MBQzJLyWb+sOK+tLGihroBNdwdej7ciUD8+yissEAbub2x7dijLRf/9HQ==', true);

-- 7.3 Empleado
INSERT INTO public.tce03_empleado (id_empleado, fk_id_sucursal)
VALUES (2, 1);

-- 7.4 Horarios (Shifts for Mon-Fri)
-- Lunes (1) to Viernes (5): 09:00 - 13:00 and 14:00 - 18:00
INSERT INTO public.tce08_horario (id_horario, fk_id_sucursal, fk_id_dia, tm_inicio, tm_fin)
VALUES 
(1, 1, 1, '09:00:00', '13:00:00'), (2, 1, 1, '14:00:00', '18:00:00'), -- Lunes
(3, 1, 2, '09:00:00', '13:00:00'), (4, 1, 2, '14:00:00', '18:00:00'), -- Martes
(5, 1, 3, '09:00:00', '13:00:00'), (6, 1, 3, '14:00:00', '18:00:00'), -- Miércoles
(7, 1, 4, '09:00:00', '13:00:00'), (8, 1, 4, '14:00:00', '18:00:00'), -- Jueves
(9, 1, 5, '09:00:00', '13:00:00'), (10, 1, 5, '14:00:00', '18:00:00'); -- Viernes

SELECT setval('public.tce08_horario_id_horario_seq', (SELECT MAX(id_horario) FROM public.tce08_horario));

-- 7.5 Empleado Horario (Assign all created schedules to Juan Pérez)
INSERT INTO public.tce06_empleado_horario (fk_id_horario, fk_id_persona)
VALUES 
(1, 2), (2, 2),
(3, 2), (4, 2),
(5, 2), (6, 2),
(7, 2), (8, 2),
(9, 2), (10, 2);

-- 7.6 Día Descanso (Optional: Next Sunday)
INSERT INTO public.tce05_dia_descanso (id_dia_descanso, fk_id_empleado, fh_descanso)
VALUES (1, 2, '2026-01-11'); -- Sunday

SELECT setval('public.tce05_dia_descanso_id_dia_descanso_seq', (SELECT MAX(id_dia_descanso) FROM public.tce05_dia_descanso));
