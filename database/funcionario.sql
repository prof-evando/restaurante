CREATE TABLE funcionario (
    id_funcionario NUMBER PRIMARY KEY,  
    nome VARCHAR2(100) NOT NULL,        
    cpf VARCHAR2(11) NOT NULL UNIQUE,   
    cargo VARCHAR2(50),                 
    salario NUMBER(10, 2)               
);