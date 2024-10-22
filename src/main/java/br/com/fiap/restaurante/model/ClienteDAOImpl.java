package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fiap.restaurante.util.ConexaoBD;

public class ClienteDAOImpl implements ClienteDAO {

	
	private Connection conn = null;
	
	public ClienteDAOImpl() {
		ConexaoBD.getInstance();
		conn = ConexaoBD.getInstance().getConn();
		
		try {
			conn.setAutoCommit(false);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void criarTabelaCliente() {
	    String tabelaQuery = "SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME = 'CLIENTE'";
	    String sql = "CREATE TABLE CLIENTE (" +
	                 "endereco_Id NUMBER NOT NULL, " +
	                 "email VARCHAR2(120) NOT NULL, " +
	                 "nome VARCHAR2(50) NOT NULL, " +
	                 "CONSTRAINT pk_cliente_email PRIMARY KEY(email), " +
	                 "FOREIGN KEY (endereco_Id) REFERENCES ENDERECO(id))";

	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(tabelaQuery);

	        // Verifica se a tabela já existe
	        if (!rs.next()) {
	            stmt.executeUpdate(sql);
	            conn.commit();  // Confirma a criação da tabela
	            System.out.println("Tabela CLIENTE criada com sucesso.");
	        } else {
	            System.out.println("A tabela CLIENTE já existe.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao criar a tabela CLIENTE: " + e.getMessage());
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
	public void incluirCliente(Endereco endereco, String nome, String email) {
		 EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
		    
		   
		    Endereco enderecoInserido = enderecoDAO.incluirEndereco(endereco);
		    
		    if (enderecoInserido != null) {
		        
		        String sqlCliente = "INSERT INTO CLIENTE (ENDERECO_ID, NOME, EMAIL) VALUES (?, ?, ?)";
		        
		        try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
		        	stmtCliente.setLong(1, enderecoInserido.getId()); // Usa o ID do endereço inserido
		        	stmtCliente.setString(2, nome);
		        	stmtCliente.setString(3, email);
		            
		            int rowsAffectedEstabelecimento = stmtCliente.executeUpdate();
		            
		            if (rowsAffectedEstabelecimento > 0) {
		                conn.commit(); // Confirma a transação
		                System.out.println("Cliente inserido com sucesso!");
		            } else {
		                System.out.println("Nenhuma linha inserida para o estabelecimento.");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            try {
		                conn.rollback(); // Desfaz a transação em caso de erro
		            } catch (SQLException rollbackException) {
		                rollbackException.printStackTrace();
		            }
		        }
		    } else {
		        System.out.println("Falha ao inserir o endereço. O estabelecimento não foi inserido.");
		    }
		}
}
