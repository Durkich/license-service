CREATE TABLE IF NOT EXISTS public.types
(
    id serial NOT NULL,
    name character varying(100) NOT NULL,
    CONSTRAINT types_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.licenses
(
    id serial NOT NULL,
    namePo character varying(255) NOT NULL,
    idType integer NOT NULL,
    startDate Date NOT NULL,
    endDate Date NOT NULL,
    countStart integer NOT NULL,
    countNow integer NOT NULL,
    CONSTRAINT licenses_pkey PRIMARY KEY (id),
    CONSTRAINT idType FOREIGN KEY (idType)
        REFERENCES public.types (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS outbox_events (
    id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(20),
    license_id INT,
    software_name VARCHAR(255),
    software_version VARCHAR(100),
    is_licensed BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sent BOOLEAN DEFAULT FALSE
);