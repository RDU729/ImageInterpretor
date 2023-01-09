CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE utilizatori
(
    name            VARCHAR(50)  NOT NULL,
    email           VARCHAR2(50) NOT NULL,
    password        LONG         NOT NULL,
    enabled         INT          NOT NULL,
    activation_code VARCHAR(200)         NOT NULL,
    CONSTRAINT username_pk PRIMARY KEY (email)
);

CREATE TABLE authorities
(
    id        NUMBER default hibernate_sequence.nextval,
    email     VARCHAR2(50) NOT NULL,
    authority VARCHAR2(50) NOT NULL,
    FOREIGN KEY (email) REFERENCES utilizatori (email)
);

CREATE INDEX ix_auth_username
    on authorities (email, authority);