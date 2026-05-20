CREATE TABLE contact (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    address VARCHAR(255),
    phone_number VARCHAR(20),
    student_id BIGINT REFERENCES person(id) NOT NULL
);