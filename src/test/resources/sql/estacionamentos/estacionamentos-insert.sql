insert into USUARIOS (id, username, password, role) values (100, 'ana@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101, 'bia@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102, 'bob@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (103, 'rob@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');


insert into CLIENTES(id,nome,cpf,id_usuario) values (10, 'Bianca Silva','79074426050', 101);
insert into CLIENTES(id,nome,cpf,id_usuario) values (20, 'Roberto Gomes','55352517047', 102);
insert into CLIENTES(id,nome,cpf,id_usuario) values (30, 'Roberval Machado','70799494089', 103);

INSERT INTO VAGAS(id, codigo, status) VALUES (1, 'A-56', 'LIVRE');
INSERT INTO VAGAS(id, codigo, status) VALUES (2, 'B-48', 'LIVRE');
INSERT INTO VAGAS(id, codigo, status) VALUES (3, 'B-56', 'LIVRE');
INSERT INTO VAGAS(id, codigo, status) VALUES (4, 'A-68', 'LIVRE');
INSERT INTO VAGAS(id, codigo, status) VALUES (5, 'A-36', 'LIVRE');
INSERT INTO VAGAS(id, codigo, status) VALUES (6, 'B-92', 'LIVRE');
INSERT INTO VAGAS(id, codigo, status) VALUES (7, 'B-31', 'LIVRE');
INSERT INTO VAGAS(id, codigo, status) VALUES (8, 'A-51', 'LIVRE');

INSERT INTO CLIENTES_TEM_VAGA (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20230313-101300', "FIT-1020", 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00', 20, 1);
INSERT INTO CLIENTES_TEM_VAGA (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('202303115-112000', "COR-3025", 'CHEVROLET', 'CORSA', 'PRETO', '2023-03-15 11:20:00', 10, 2);
INSERT INTO CLIESNTE_TEM_VAGA (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20230316-133300', "FIT-1020", 'FIAT', 'PALIO', 'VERDE', '2023-03-16 13:33:00', 30, 3);