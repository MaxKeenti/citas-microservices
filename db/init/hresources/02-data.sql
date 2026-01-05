-- Initial Data for Human Resources

-- Establecimiento
INSERT INTO public.tce01_establecimiento (id_establecimiento, tx_nombre) VALUES (1, 'BarberIA');

-- Sucursal
-- Assuming SRID 4326 for straightforward lon/lat
INSERT INTO public.tce02_sucursal (id_sucursal, fk_id_establecimiento, tx_nombre, gm_ubicacion) 
VALUES (1, 1, 'Centro Histórico', ST_SetSRID(ST_MakePoint(-99.1332, 19.4326), 4326));

-- Empleado (Matches ID 2 in Access Control)
INSERT INTO public.tce03_empleado (id_empleado, fk_id_sucursal) VALUES (2, 1);

-- Horarios (General Shift: 9-18) for Sucursal 1
INSERT INTO public.tce08_horario (id_horario, fk_id_sucursal, fk_id_dia, tm_inicio, tm_fin) VALUES
(1, 1, 1, '09:00:00', '18:00:00'), -- Lunes
(2, 1, 2, '09:00:00', '18:00:00'), -- Martes
(3, 1, 3, '09:00:00', '18:00:00'), -- Miércoles
(4, 1, 4, '09:00:00', '18:00:00'), -- Jueves
(5, 1, 5, '09:00:00', '18:00:00'); -- Viernes

-- Assign Horarios to Empleado 2
INSERT INTO public.tce06_empleado_horario (fk_id_horario, fk_id_persona) VALUES
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 2);

-- Reset Sequences
ALTER SEQUENCE public.tce01_establecimiento_id_establecimiento_seq RESTART WITH 2;
ALTER SEQUENCE public.tce02_sucursal_id_sucursal_seq RESTART WITH 2;
ALTER SEQUENCE public.tce08_horario_id_horario_seq RESTART WITH 6;
