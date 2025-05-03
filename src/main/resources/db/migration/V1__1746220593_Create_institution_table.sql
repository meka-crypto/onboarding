CREATE TABLE public.institution (
    id BIGSERIAL PRIMARY KEY,
    code BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    status INTEGER NOT NULL
);

ALTER TABLE institution ADD CONSTRAINT uq_institution_code UNIQUE (code);