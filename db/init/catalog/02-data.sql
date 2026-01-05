-- Initial Data for Catalog Service
INSERT INTO public.cci01_servicio (id_servicio, tx_nombre, tx_descripcion, st_activo, nu_duracion) VALUES 
(1, 'General Consultation', 'Basic medical consultation for general health checkup.', true, 30),
(2, 'Dental Cleaning', 'Professional dental cleaning and checkup.', true, 45),
(3, 'Cardiology Screening', 'Heart health screening and EKG.', true, 60),
(4, 'Pediatric Checkup', 'Routine checkup for children.', true, 30),
(5, 'Eye Exam', 'Comprehensive eye examination.', true, 45);

ALTER SEQUENCE public.cci01_servicio_id_servicio_seq RESTART WITH 6;
