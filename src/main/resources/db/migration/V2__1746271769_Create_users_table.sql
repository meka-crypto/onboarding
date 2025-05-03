CREATE TABLE public.user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status INTEGER NOT NULL
);

ALTER TABLE public.user ADD CONSTRAINT uq_users_username UNIQUE (username);