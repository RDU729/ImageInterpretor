CREATE SEQUENCE flow_sequence
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE flow
(
    id            NUMBER default flow_sequence.nextval,
    pid           varchar2(200),
    user_email    varchar2(200),
    img_id        NUMBER,
    creation_date TIMESTAMP,
    last_update   TIMESTAMP,
    CONSTRAINT flow_pk PRIMARY KEY (id),
    FOREIGN KEY (user_email) REFERENCES utilizatori (email),
    FOREIGN KEY (img_id) REFERENCES img (id)
);