CREATE TABLE sensors
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    model       VARCHAR(15) NOT NULL,
    range_from  INT         NOT NULL CHECK (range_from >= 0),
    range_to    INT         NOT NULL CHECK (range_to > range_from),
    type        VARCHAR(20) NOT NULL,
    unit        VARCHAR(10),
    location    VARCHAR(40),
    description VARCHAR(200),

    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);