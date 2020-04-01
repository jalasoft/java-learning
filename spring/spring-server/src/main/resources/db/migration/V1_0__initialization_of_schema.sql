CREATE TABLE address (
    id UUID,
    street VARCHAR(25) NOT NULL,
    number INTEGER NOT NULL CHECK (number > 0),
    town VARCHAR(20) NOT NULL,
    pobox VARCHAR(8) NOT NULL,
    country VARCHAR(15) NOT NULL,

    CONSTRAINT PK_ADDRESS PRIMARY KEY (id)
);

CREATE TABLE person (
    id UUID,
    name VARCHAR(10) NOT NULL,
    surname VARCHAR(25) NOT NULL,
    email VARCHAR(25) NOT NULL,
    address UUID,

    CONSTRAINT PK_PERSON PRIMARY KEY (id),
    CONSTRAINT FK_PERSON FOREIGN KEY (address) REFERENCES address(id)
);

CREATE TABLE credentials (
    id UUID,
    username VARCHAR(10) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    person_id UUID,

    CONSTRAINT PK_CREDENTIALS PRIMARY KEY (id),
    CONSTRAINT FK_USER FOREIGN KEY (person_id) REFERENCES person(id)
);







