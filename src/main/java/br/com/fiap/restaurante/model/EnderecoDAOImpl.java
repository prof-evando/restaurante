package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fiap.restaurante.util.ConexaoBD;

public class EnderecoDAOImpl implements EnderecoDAO {

	private Connection conn = null;
	
	public EnderecoDAOImpl() {
		ConexaoBD.getInstance();
		conn = ConexaoBD.getInstance().getConn();
		
		try {
			conn.setAutoCommit(false);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void criarTabelaEndereco() {
	    String tabelaQuery = "SELECT TABLE_NAME FROM ALL_TABLES WHERE TABLE_NAME = 'ENDERECO'";
	    String sql = "CREATE TABLE ENDERECO (" +
	                 "id NUMBER NOT NULL, " +
	                 "cidade VARCHAR2(30) NOT NULL, " +
	                 "bairro VARCHAR2(30) NOT NULL, " +
	                 "cep VARCHAR2(30) NOT NULL, " +
	                 "CONSTRAINT pk_endereco_id PRIMARY KEY(id))";

	    try (Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(tabelaQuery)) {
	        
	        if (!rs.next()) { 
	            stmt.executeUpdate(sql);
	            System.out.println("Tabela ENDERECO criada com sucesso.");
	        } else {
	            System.out.println("A tabela ENDERECO já existe.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public Endereco incluirEndereco(Endereco endereco) {
		
	    String sql = "INSERT INTO ENDERECO (CIDADE, BAIRRO, CEP) VALUES (?, ?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, endereco.getCidade());
	        stmt.setString(2, endereco.getBairro());
	        stmt.setString(3, endereco.getCep());

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            conn.commit(); 
	            System.out.println("Endereço inserido com sucesso!");
	            
	            String query = "SELECT id FROM ENDERECO WHERE CEP = ? AND CIDADE = ? AND BAIRRO = ?";
	            try (PreparedStatement stmtSelect = conn.prepareStatement(query)) {
	                stmtSelect.setString(1, endereco.getCep());
	                stmtSelect.setString(2, endereco.getCidade());
	                stmtSelect.setString(3, endereco.getBairro());

	                try (ResultSet rs = stmtSelect.executeQuery()) {
	                    if (rs.next()) {
	                        int id = rs.getInt("id");
	                        endereco.setId(id); 
	                        System.out.println("ID do endereço: " + id);
	                    }
	                }
	            }

	            return endereco; 
	        } else {
	            System.out.println("Nenhuma linha inserida.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            conn.rollback(); 
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	    }
	    
	    return null; 
	}

	@Override
	public void criarSequenceEndereco() {
	    String sequenceQuery = "SELECT SEQUENCE_NAME FROM ALL_SEQUENCES WHERE SEQUENCE_NAME = 'ENDERECO_SEQ'";
	    String sql = "CREATE SEQUENCE endereco_seq START WITH 1 INCREMENT BY 1 NOCACHE";
	    
	    try (Statement stmt = conn.createStatement(); 
	         ResultSet rs = stmt.executeQuery(sequenceQuery)) {
	        
	       
	        if (!rs.next()) { 
	            stmt.executeUpdate(sql);
	            System.out.println("Sequence Endereço criada");
	        } else {
	            System.out.println("A sequence ENDEREÇO já existe");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void criarTriggerEndereco() {
	    String sql = "CREATE OR REPLACE TRIGGER endereco_bir " +
	                 "BEFORE INSERT ON ENDERECO " +
	                 "FOR EACH ROW " +
	                 "BEGIN " +
	                 "  IF :NEW.id IS NULL THEN " +
	                 "    SELECT endereco_seq.NEXTVAL INTO :NEW.id FROM dual; " +
	                 "  END IF; " +
	                 "END;";

	    try (Statement stmt = conn.createStatement()) {
	        stmt.executeUpdate(sql);
	        System.out.println("Trigger 'endereco_bir' compilado com sucesso.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
