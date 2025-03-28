CREATE TABLE employee (
    id          SERIAL          PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,
    department  VARCHAR(100)    NOT NULL,
    salary      INT             NOT NULL,
    age         INT             NOT NULL,
    email       VARCHAR(100)    NOT NULL,
);

INSERT INTO employee (id, name, department, salary, age, email) VALUES ('John Doe', 'IT', 2000, 27, 'jhon@ua.pt');
INSERT INTO employee (id, name, department, salary, age, email) VALUES ('Maria', 'IT', 1000, 23, 'maria@ua.pt');
INSERT INTO employee (id, name, department, salary, age, email) VALUES ('Marco', 'IT', 1500, 24, 'marco@ua.pt');
INSERT INTO employee (id, name, department, salary, age, email) VALUES ('Ines', 'IT', 2000, 27, 'ines@ua.pt');
INSERT INTO employee (id, name, department, salary, age, email) VALUES ('Joao', 'IT', 2000, 30, 'joao@ua.pt');