package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.fiap.restaurante.exceptions.PedidoException;
import br.com.fiap.restaurante.util.ConexaoBD;

public class PedidoDAOImpl implements PedidoDAO {

	public PedidoDAOImpl() {
		super();
		criarTabelaPedidos();
	}

	@Override
	public void adicionar(Pedido pedido) {
		String sql = "INSERT INTO PEDIDO (numero, descricao, valor_total, pago) VALUES (?, ?, ?, ?)";

		try (Connection connection = ConexaoBD.getInstance().getConn();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			// Preencher os parâmetros
			stmt.setInt(1, pedido.getNumero());
			stmt.setString(2, pedido.getDescricao());
			stmt.setDouble(3, pedido.getValorTotal());
			stmt.setBoolean(4, pedido.isPago());

			// Executar a query
			stmt.executeUpdate();
			System.out.println("Pedido adicionado com sucesso.");
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(Pedido pedido) {
		String sql = "UPDATE PEDIDO SET numero = ?, descricao = ?, valor_total = ?, pago = ? WHERE id = ?";
		try (Connection connection = ConexaoBD.getInstance().getConn();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			// Preencher os parâmetros
			stmt.setInt(1, pedido.getNumero());
			stmt.setString(2, pedido.getDescricao());
			stmt.setDouble(3, pedido.getValorTotal());
			stmt.setBoolean(4, pedido.isPago());

			// Executar a query
			stmt.executeUpdate();
			System.out.println("Pedido atualizado com sucesso.");
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void apagar(int id) {
		String sql = "DELETE FROM PEDIDO WHERE id = ?";

		try (Connection connection = ConexaoBD.getInstance().getConn();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			stmt.executeUpdate();
			System.out.println("Pedido removido com sucesso.");
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Pedido listarPedido(int numero) {
		String sqlPedidos = "SELECT * FROM PEDIDO WHERE numero = " + numero;

		try (Connection connection = ConexaoBD.getInstance().getConn();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sqlPedidos)) {
			if (rs.next()) {
				// Cria o pedido
				Pedido pedido = new Pedido(rs.getInt("numero"), rs.getString("descricao"), rs.getDouble("valor_total"),
						rs.getBoolean("pago"), new ArrayList<>());

				// Consulta os itens correspondentes a este pedido
				String sqlItens = "SELECT * FROM ITEM WHERE numero_pedido = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(sqlItens)) {
					pstmt.setInt(1, rs.getInt("numero"));
					ResultSet rsItens = pstmt.executeQuery();

					while (rsItens.next()) {
						Item item = new Item(rsItens.getString("nome"), rsItens.getInt("quantidade"),
								rsItens.getDouble("preco_unitario"), rsItens.getInt("numero_pedido"));
						// Adiciona o item à lista de itens do pedido
						pedido.getItens().add(item);
					}
					return pedido;
				} catch (Exception e){
					e.printStackTrace();
				}
			} else {
				throw new PedidoException("Não existe esse pedido na base.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void criarTabelaPedidos() {
		Connection conexao = null;
		Statement stmt = null;
		try {
			conexao = ConexaoBD.getInstance().getConn();
			stmt = conexao.createStatement();

			// SQL para criar a tabela Pedido
			String sqlTabela = "CREATE TABLE PEDIDO (" + "id NUMBER PRIMARY KEY, " + "numero NUMBER UNIQUE, "
					+ "descricao VARCHAR2(255), " + "valor_total NUMBER(10, 2), " + "pago NUMBER(1)" + ")";

			// Executar a criação da tabela
			stmt.executeUpdate(sqlTabela);

			// SQL para criar a sequence (caso não exista)
			String sqlSequence = "CREATE SEQUENCE pedido_seq " + "START WITH 1 " + // Começa com 1
					"INCREMENT BY 1 " + // Incrementa de 1 em 1
					"NOCACHE"; // Não armazena em cache

			stmt.executeUpdate(sqlSequence);

			// SQL para criar o trigger que usa a sequence
			String sqlTrigger = "CREATE OR REPLACE TRIGGER pedido_trigger " + "BEFORE INSERT ON PEDIDO "
					+ "FOR EACH ROW " + "BEGIN " + "   :NEW.id := pedido_seq.NEXTVAL; " + "END;";

			stmt.executeUpdate(sqlTrigger);

			System.out.println(
					"Tabela 'PEDIDO', sequence 'pedido_seq', e trigger 'pedido_trigger' criados/verificados com sucesso!");

		} catch (SQLException e) {
			System.out.println("Erro ao criar/verificar a tabela 'PEDIDO', sequence ou trigger.");
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
}
