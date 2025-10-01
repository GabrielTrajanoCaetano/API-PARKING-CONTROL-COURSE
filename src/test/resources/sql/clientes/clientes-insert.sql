insert into USUARIOS (id, username, password, role)
    values (100, 'ana@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role)
    values (101, 'bia@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (102, 'bob@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (103, 'toby@email.com', '$2a$12$.xQ0fwXK4cYAbhzXisYXB./rgLHw7aPz7ln7D4SyTFGc3PRHg0Wcm', 'ROLE_CLIENTE');

insert into CLIENTES(id,nome,cpf,id_usuario) values (10, 'Bianca Silva','79074426050', 101);
insert into CLIENTES(id,nome,cpf,id_usuario) values (20, 'Roberto Gomes','55352517047', 102);

