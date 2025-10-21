insert into USUARIOS (id, username, password, role)
    values (110, 'ana@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role)
    values (101, 'bia@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (102, 'bob@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (103, 'toby@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (104, 'cesar@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');


insert into CLIENTES(id,nome,cpf,id_usuario) values (10, 'Bianca Silva','79074426050', 101);
insert into CLIENTES(id,nome,cpf,id_usuario) values (20, 'Roberto Gomes','55352517047', 102);
insert into CLIENTES(id,nome,cpf,id_usuario) values (30, 'Tobias Machado','70799494089', 103);

INSERT INTO VAGAS(id, codigo, status) VALUES (1, 'A-56', 'OCUPADA');
INSERT INTO VAGAS(id, codigo, status) VALUES (2, 'B-48', 'OCUPADA');
INSERT INTO VAGAS(id, codigo, status) VALUES (3, 'B-56', 'OCUPADA');

INSERT INTO clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00', 20, 1);
INSERT INTO clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('202303115-112000', 'COR-3025', 'CHEVROLET', 'CORSA', 'PRETO', '2023-03-15 11:20:00', 10, 2);
INSERT INTO clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20230316-133300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-16 13:33:00', 30, 3);