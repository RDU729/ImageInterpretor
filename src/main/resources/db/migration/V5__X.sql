CREATE TABLE utilizatori
(
    name VARCHAR(50) NOT NULL,
    email VARCHAR2(50)  NOT NULL,
    password VARCHAR2(100) NOT NULL,
    enabled  INT           NOT NULL,
    CONSTRAINT username_pk PRIMARY KEY (email)
);

CREATE TABLE authorities
(
    email  VARCHAR2(50) NOT NULL,
    authority VARCHAR2(50) NOT NULL,
    FOREIGN KEY (email) REFERENCES utilizatori (email)
);

CREATE  INDEX ix_auth_username
    on authorities (email, authority);