-- Initial Admin User Creation
-- Password should be changed immediately after deployment
-- The password hash here 'potaxie' is a placeholder. In a real scenario, use bcrypt/argon2 hashes if the app supports it.
-- Assuming the app stores plain text or specific hash (based on previous analysis: public.tca02_usuario had plain text).

INSERT INTO public.tca01_persona (id_persona, fk_id_genero, tx_nombre, tx_primer_apellido, tx_segundo_apellido, fh_nacimiento)
VALUES (1, 1, 'Admin', 'System', 'User', '2000-01-01');

INSERT INTO public.tca02_usuario (id_usuario, fk_id_rol, tx_login, tx_password, st_activo)
VALUES (1, 1, 'admin@example.com', 'f89Lo5HEh4Tt3lmYidbj8eR6J9s27MBQzJLyWb+sOK+tLGihroBNdwdej7ciUD8+yissEAbub2x7dijLRf/9HQ==', true);

-- Reset sequences to ensure next inserts don't collide
SELECT setval('public.tca01_persona_id_persona_seq', (SELECT MAX(id_persona) FROM public.tca01_persona));
