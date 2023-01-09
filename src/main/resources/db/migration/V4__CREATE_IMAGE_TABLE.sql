CREATE TABLE img
(
    id     NUMBER default hibernate_sequence.nextval,
    base64 LONG NOT NULL,
    CONSTRAINT img_pk PRIMARY KEY (id)
);