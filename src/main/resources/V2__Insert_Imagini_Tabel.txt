create sequence my_test_id_seq increment by 1;

create sequence hibernate_sequence increment by 1;

create table img
(
    id   number default hibernate_sequence.nextval,
    base64 long
);