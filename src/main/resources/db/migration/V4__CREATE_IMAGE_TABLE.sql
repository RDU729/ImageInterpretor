CREATE SEQUENCE img_sequence
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE img
(
    id     NUMBER default img_sequence.nextval,
    base64 LONG NOT NULL,
    CONSTRAINT img_pk PRIMARY KEY (id)
);