CREATE TABLE IF NOT EXISTS sensor_types(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS sensor_units(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS sensors(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    model       VARCHAR(15) NOT NULL,
    range_from  INT         NOT NULL CHECK (range_from >= 0),
    range_to    INT         NOT NULL CHECK (range_to > range_from),
    type_id     INT         NOT NULL,
    unit_id     INT,
    location    VARCHAR(40),
    description VARCHAR(200),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_type FOREIGN KEY (type_id) REFERENCES sensor_types (id),
    CONSTRAINT fk_unit FOREIGN KEY (unit_id) REFERENCES sensor_units (id)
);