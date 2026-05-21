-- Dodajemy Tutora
INSERT INTO person(id, first_name, last_name, role, identity_number) VALUES (77, 'Jan', 'Kowalski', 'TUTOR', '1234');

-- Dodajemy Studenta
INSERT INTO person(id, first_name, last_name, role, identity_number) VALUES (88, 'Anna', 'Nowak', 'STUDENT', '5678');

-- Dodajemy przykładowy kontakt (dodano 'Domowy' jako name)
INSERT INTO contact(id, name, email, address, phone_number, student_id) VALUES (99, 'Domowy', 'jakub.chalimoniuk@gmail.com', 'ul. Ogrodowa 12', '123-456-789', 88);