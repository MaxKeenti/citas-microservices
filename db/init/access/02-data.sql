-- Admin User
INSERT INTO public.tca01_persona (id_persona, fk_id_genero, tx_nombre, tx_primer_apellido, tx_segundo_apellido, fh_nacimiento)
VALUES (1, 1, 'Admin', 'System', NULL, '1990-01-01');

INSERT INTO public.tca02_usuario (id_usuario, fk_id_rol, tx_login, tx_password, st_activo)
VALUES (1, 1, 'admin', 'password', true);

-- Barber User (Employee)
INSERT INTO public.tca01_persona (id_persona, fk_id_genero, tx_nombre, tx_primer_apellido, tx_segundo_apellido, fh_nacimiento)
VALUES (2, 1, 'Juan', 'Barbero', NULL, '1985-05-20');

INSERT INTO public.tca02_usuario (id_usuario, fk_id_rol, tx_login, tx_password, st_activo)
VALUES (2, 2, 'barber', 'password', true);

-- Reset sequences to avoid collision
SELECT setval('tca01_persona_id_persona_seq', (SELECT MAX(id_persona) FROM tca01_persona));
