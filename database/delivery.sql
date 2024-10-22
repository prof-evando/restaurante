CREATE TABLE ENDERECO (
    id NUMBER NOT NULL,
    cidade VARCHAR2(30) NOT NULL,
    bairro VARCHAR2(30) NOT NULL,
    cep VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_endereco_id PRIMARY KEY(id) -- Nome �nico para a constraint
);

CREATE TABLE ESTABELECIMENTO (
    endereco_Id NUMBER NOT NULL, -- Deve ser NOT NULL pois � uma chave estrangeira
    nome VARCHAR2(50) NOT NULL, -- Ajustado para um tamanho maior, se necess�rio
    tel VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_estabelecimento_nome PRIMARY KEY(nome),
    CONSTRAINT fk_endereco_estabelecimento
    FOREIGN KEY (endereco_Id) REFERENCES ENDERECO(id) -- Nome da foreign key
);

CREATE TABLE CLIENTE (
     endereco_Id NUMBER NOT NULL, -- Deve ser NOT NULL pois � uma chave estrangeira
     email VARCHAR2(120) NOT NULL,
     nome VARCHAR2(50) NOT NULL, -- Ajustado para um tamanho maior, se necess�rio
     CONSTRAINT pk_cliente_email PRIMARY KEY(email),
     FOREIGN KEY (endereco_Id) REFERENCES ENDERECO(id) -- Nome da foreign key
);

CREATE TABLE MOTOBOY (
    id NUMBER NOT NULL,
    nome VARCHAR2(30) NOT NULL,
    veiculo VARCHAR2(30) NOT NULL,
    eh_ocupado VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_motoboy_id PRIMARY KEY(id)
);

CREATE SEQUENCE endereco_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE OR REPLACE TRIGGER endereco_bir 
BEFORE INSERT ON ENDERECO 
FOR EACH ROW
BEGIN
  IF :NEW.id IS NULL THEN
    SELECT endereco_seq.NEXTVAL INTO :NEW.id FROM dual;
  END IF;
END;

CREATE SEQUENCE motoboy_seq
  START WITH 1
  INCREMENT BY 1;

CREATE OR REPLACE TRIGGER motoboy_id_trigger
BEFORE INSERT ON MOTOBOY
FOR EACH ROW
BEGIN
  :NEW.ID := motoboy_seq.NEXTVAL;
END;


SELECT * FROM ESTABELECIMENTO;
SELECT * FROM ENDERECO;
SELECT * FROM CLIENTE;
SELECT * FROM MOTOBOY;
SELECT * FROM PEDIDO;
-- DROP TABLE PEDIDO;

-- DELETE FROM ESTABELECIMENTO;
-- DELETE FROM ENDERECO;
-- DELETE FROM CLIENTE;
-- DELETE FROM MOTOBOY;
-- DELETE FROM PEDIDO;

-- DROP TABLE PEDIDO;
-- DROP TABLE ESTABELECIMENTO;
-- DROP TABLE CLIENTE;
-- DROP TABLE MOTOBOY;
-- DROP TABLE ENDERECO;

-- DROP SEQUENCE endereco_seq;
-- DROP SEQUENCE motoboy_seq;
-- DROP SEQUENCE pedido_seq;