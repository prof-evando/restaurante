DROP TABLE PEDIDO;
CREATE TABLE PEDIDO (  id NUMBER PRIMARY KEY,   numero NUMBER UNIQUE, 
					 descricao VARCHAR2(255),   valor_total NUMBER(10, 2),   pago NUMBER(1)  );
					
					 
CREATE SEQUENCE pedido_seq   START WITH 1   
					INCREMENT BY 1  
					NOCACHE; 

					
CREATE OR REPLACE TRIGGER pedido_trigger   BEFORE INSERT ON PEDIDO 
					 FOR EACH ROW   BEGIN      :NEW.id := pedido_seq.NEXTVAL;   END;;
					
					
					
 DROP TABLE ITEM;				
 CREATE TABLE ITEM ( 
	                           id NUMBER PRIMARY KEY,  
	                           nome VARCHAR2(255),  
	                           quantidade NUMERIC(2),  
	                           preco_unitario NUMBER(10, 2), 
	                           numero_pedido NUMBER,  
	                           FOREIGN KEY (numero_pedido) REFERENCES PEDIDO(numero) 
	                           );
	                           
	                          
CREATE SEQUENCE item_seq  
	                             START WITH 1         
	                             NOCACHE;  
	                             
	                             
	                             
CREATE OR REPLACE TRIGGER item_trigger  
	                            BEFORE INSERT ON ITEM  
	                            FOR EACH ROW  
	                            BEGIN  
	                               :NEW.id := item_seq.NEXTVAL;  
	                            END;;