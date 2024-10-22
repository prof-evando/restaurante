package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fiap.restaurante.util.ConexaoBD;


public class EstabelecimentoDAOImpl implements EstabelecimentoDAO {
	
	private Connection conn = null;
	
	public EstabelecimentoDAOImpl() {
		ConexaoBD.getInstance();
		conn = ConexaoBD.getInstance().getConn();
		
		try {
			conn.setAutoCommit(false);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void criarTabelaEstabelecimento() {
		String tabelaQuery = "SELECT TABLE_NAME FROM ALL_TABLES WHERE TABLE_NAME = 'ESTABELECIMENTO'";
	    String sql = "CREATE TABLE ESTABELECIMENTO (" +
	                 "endereco_Id NUMBER NOT NULL, " +
	                 "nome VARCHAR2(50) NOT NULL, " +
	                 "tel VARCHAR2(20) NOT NULL," + 
	                 "CONSTRAINT pk_estabelecimento_nome PRIMARY KEY(nome)," +
	                 "CONSTRAINT fk_endereco_estabelecimento FOREIGN KEY (endereco_Id) REFERENCES ENDERECO(id))";
	                

	    try (Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(tabelaQuery)) {
	        
	        if (!rs.next()) { 
	            stmt.executeUpdate(sql);
	            System.out.println("Tabela ESTABELECIMENTO criada com sucesso.");
	        } else {
	            System.out.println("A tabela ESTABELECIMENTO já existe.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}

	@Override
	public void incluirEstabelecimento(Endereco endereco, Estabelecimento estabelecimento) {

	    EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
	   
	    Endereco enderecoInserido = enderecoDAO.incluirEndereco(endereco);
	    
	    if (enderecoInserido != null) {
	       
	        String sqlEstabelecimento = "INSERT INTO ESTABELECIMENTO (ENDERECO_ID, NOME, TEL) VALUES (?, ?, ?)";
	        System.out.println("Incluindo um estabelecimento: ");
	        try (PreparedStatement stmtEstabelecimento = conn.prepareStatement(sqlEstabelecimento)) {
	            stmtEstabelecimento.setLong(1, enderecoInserido.getId()); // Usa o ID do endereço inserido
	            stmtEstabelecimento.setString(2, estabelecimento.getNome());
	            stmtEstabelecimento.setString(3, estabelecimento.getTel());
	            
	            int rowsAffectedEstabelecimento = stmtEstabelecimento.executeUpdate();
	            
	            if (rowsAffectedEstabelecimento > 0) {
	                conn.commit(); // Confirma a transação
	                System.out.println("Estabelecimento inserido com sucesso!");
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
