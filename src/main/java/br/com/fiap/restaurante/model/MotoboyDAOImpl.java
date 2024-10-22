package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fiap.restaurante.util.ConexaoBD;

public class MotoboyDAOImpl implements MotoboyDAO {
	
	private Connection conn = null;
	
	public MotoboyDAOImpl() {
		ConexaoBD.getInstance();
		conn = ConexaoBD.getInstance().getConn();
		
		try {
			conn.setAutoCommit(false);
		} catch(SQLException e) {
			e.printStackTrace();
		}
}
	@Override
	public void criarTabelaMotoboy() {
		String tabelaQuery = "SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME = 'MOTOBOY'";
	    String sql = "CREATE TABLE MOTOBOY (" +
	                 "id NUMBER NOT NULL, " +
	                 "nome VARCHAR2(30) NOT NULL, " +
	                 "veiculo VARCHAR2(30) NOT NULL, " +
	                 "eh_ocupado VARCHAR2(20) NOT NULL, " +
	                 "CONSTRAINT pk_motoboy_id PRIMARY KEY(id))";

	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(tabelaQuery);

	        // Verifica se a tabela já existe
	        if (!rs.next()) {
	            stmt.executeUpdate(sql);
	            conn.commit();  // Confirma a criação da tabela
	            System.out.println("Tabela MOTOBOY criada com sucesso.");
	        } else {
	            System.out.println("A tabela MOTOBOY já existe.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao criar a tabela MOTOBOY: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	}

	@Override
	public void incluirMotoboy(Motoboy motoboy) {
		String sql = "INSERT INTO MOTOBOY (NOME, VEICULO, EH_OCUPADO) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID"})) { // O "new String[]{"ID"}" faz referência ao campo de chave primária
            stmt.setString(1, motoboy.getNome());
            stmt.setString(2, motoboy.getVeiculo());
            stmt.setBoolean(3, motoboy.getEhOcupado());

           

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                	motoboy.setId(generatedKeys.getInt(1));
                }
                conn.commit();
                System.out.println("Motoboy inserido com sucesso! ID: " + motoboy.getId());
            } else {
                System.out.println("Nenhuma linha inserida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // Desfaz a transação em caso de erro
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }

	}
	@Override
	public void criarSequenceMotoboy() {
		String sequenceQuery = "SELECT SEQUENCE_NAME FROM ALL_SEQUENCES WHERE SEQUENCE_NAME = 'MOTOBOY_SEQ'";
	    String sql = "CREATE SEQUENCE motoboy_seq START WITH 1 INCREMENT BY 1 NOCACHE";
	    
	    try (Statement stmt = conn.createStatement(); 
		         ResultSet rs = stmt.executeQuery(sequenceQuery)) {
		        
		       
		        if (!rs.next()) { 
		            stmt.executeUpdate(sql);
		            System.out.println("Sequence MOTOBOY criada");
		        } else {
		            System.out.println("A sequence MOTOBOY já existe");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
	}
	@Override
	public void criarTriggerMotoboy() {
		String sql = "CREATE OR REPLACE TRIGGER motoboy_id_trigger " +
                "BEFORE INSERT ON MOTOBOY " +
                "FOR EACH ROW " +
                "BEGIN " +
                "  IF :NEW.id IS NULL THEN " +
                "    SELECT motoboy_seq.NEXTVAL INTO :NEW.id FROM dual; " +
                "  END IF; " +
                "END;";

		try (Statement stmt = conn.createStatement()) {
	       stmt.executeUpdate(sql);
	       System.out.println("Trigger 'motoboy_id_trigger' compilado com sucesso.");
		} catch (SQLException e) {
	       e.printStackTrace();
		}
}
	
}
