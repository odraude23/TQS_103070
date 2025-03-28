CREATE TABLE car (
    car_id SERIAL PRIMARY KEY,
    maker VARCHAR(255),
    model VARCHAR(255)
);

INSERT INTO car (maker, model) VALUES ('Ford', 'Puma');
INSERT INTO car (maker, model) VALUES ('Ford', 'Focus');
INSERT INTO car (maker, model) VALUES ('Toyota', 'Prius');
INSERT INTO car (maker, model) VALUES ('Honda', 'Civic');