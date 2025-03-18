INSERT INTO sensors (type, unit)
VALUES ('Pressure', 'bar'),
       ('Voltage', 'voltage'),
       ('Temperature', '°С'),
       ('Humidity', '%');

CREATE TABLE sensor_types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE sensor_units
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO sensor_types (name)
VALUES ('Pressure'),
       ('Voltage'),
       ('Temperature'),
       ('Humidity');


INSERT INTO sensor_units (name)
VALUES ('bar'),
       ('voltage'),
       ('°С'),
       ('%');