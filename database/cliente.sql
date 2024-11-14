CREATE TABLE CLIENTE (
 	 ID NUMBER(10) PRIMARY KEY,
     endereco_Id NUMBER NOT NULL, -- Deve ser NOT NULL pois � uma chave estrangeira
     email VARCHAR2(120) NOT NULL,
     nome VARCHAR2(50) NOT NULL, -- Ajustado para um tamanho maior, se necess�rio
     FOREIGN KEY (endereco_Id) REFERENCES ENDERECO(id) -- Nome da foreign key
);
