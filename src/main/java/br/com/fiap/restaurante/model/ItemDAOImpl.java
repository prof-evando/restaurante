package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.fiap.restaurante.util.ConexaoBD;

public class ItemDAOImpl implements ItemDAO{
	
	public ItemDAOImpl() {
		super();
		criarTabelaItem();
	}

	@Override
	public void adicionar(Item item) {
		String sql = "INSERT INTO ITEM (nome, quantidade, preco_unitario, numero_pedido) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConexaoBD.getInstance().getConn();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Preencher os parâmetros
            stmt.setString(1, item.getNome());
            stmt.setInt(2, item.getQuantidade());
            stmt.setDouble(3, item.getPrecoUnitario());
            stmt.setInt(4, item.getNumeroPedido());
            
            // Executar a query
            stmt.executeUpdate();
            System.out.println("Item adicionado com sucesso.");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void atualizar(Item item) {
		 String sql = "UPDATE ITEM SET nome = ?, quantidade = ?, preco_unitario = ? WHERE id = ?";
	        try (Connection connection = ConexaoBD.getInstance().getConn();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            // Preencher os parâmetros
	            stmt.setString(1, item.getNome());
	            stmt.setInt(2, item.getQuantidade());
	            stmt.setDouble(3, item.getPrecoUnitario());	            

	            // Executar a query
	            stmt.executeUpdate();
	            System.out.println("Item atualizado com sucesso.");
	            connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public void apagar(int id) {
		String sql = "DELETE FROM ITEM WHERE id = ?";

        try (Connection connection = ConexaoBD.getInstance().getConn();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
            System.out.println("Item removido com sucesso.");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void criarTabelaItem() {
	    Connection conexao = null;
	    Statement stmt = null;
	    try {
	        conexao = ConexaoBD.getInstance().getConn();
	        stmt = conexao.createStatement();

	        // SQL para criar a tabela Item
	        String sqlTabela = "CREATE TABLE ITEM (" +
	                           "id NUMBER PRIMARY KEY, " +
	                           "nome VARCHAR2(255), " +
	                           "quantidade NUMERIC(2), " +
	                           "preco_unitario NUMBER(10, 2)," +
	                           "numero_pedido NUMBER, " +
	                           "FOREIGN KEY (numero_pedido) REFERENCES PEDIDO(numero)" +
	                           ")";

	        // Executar a criação da tabela
	        stmt.executeUpdate(sqlTabela);

	        // SQL para criar a sequence (caso não exista)
	        String sqlSequence = "CREATE SEQUENCE item_seq " +
	                             "START WITH 1 " +       // Começa com 1
	                             "INCREMENT BY 1 " +     // Incrementa de 1 em 1
	                             "NOCACHE";              // Não armazena em cache

	        stmt.executeUpdate(sqlSequence);

	        // SQL para criar o trigger que usa a sequence
	        String sqlTrigger = "CREATE OR REPLACE TRIGGER item_trigger " +
	                            "BEFORE INSERT ON ITEM " +
	                            "FOR EACH ROW " +
	                            "BEGIN " +
	                            "   :NEW.id := item_seq.NEXTVAL; " +
	                            "END;";

	        stmt.executeUpdate(sqlTrigger);

	        System.out.println("Tabela 'ITEM', sequence 'item_seq', e trigger 'item_trigger' criados/verificados com sucesso!");

	    } catch (SQLException e) {
	        System.out.println("Erro ao criar/verificar a tabela 'Item', sequence ou trigger.");
	        e.printStackTrace();
	    } finally {
	        // Fechar statement e conexão
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
    }

	@Override
	public Item obterPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
