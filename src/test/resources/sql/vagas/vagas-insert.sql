delete from vagas;
alter table vagas alter column id restart with 1;
delete from usuarios where username = 'ana@email.com';
insert into USUARIOS (username, password, role) values ('ana@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_ADMIN');
insert into USUARIOS (username, password, role) values ('bia@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (username, password, role) values ('bob@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');

INSERT INTO VAGAS(codigo, status) VALUES ('A-56', 'LIVRE');
INSERT INTO VAGAS(codigo, status) VALUES ('B-48', 'LIVRE');
INSERT INTO VAGAS(codigo, status) VALUES ('B-56', 'LIVRE');
INSERT INTO VAGAS(codigo, status) VALUES ('A-68', 'LIVRE');
INSERT INTO VAGAS(codigo, status) VALUES ('A-36', 'LIVRE');
INSERT INTO VAGAS(codigo, status) VALUES ('B-92', 'LIVRE');
INSERT INTO VAGAS(codigo, status) VALUES ('B-31', 'LIVRE');
INSERT INTO VAGAS(codigo, status) VALUES ('A-51', 'LIVRE');
