package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	    			 "id NUMBER NOT NULL, " +
	                 "endereco_Id NUMBER NOT NULL, " +
	                 "email VARCHAR2(120) NOT NULL, " +
	                 "nome VARCHAR2(50) NOT NULL, " +
	                 "CONSTRAINT pk_cliente_id PRIMARY KEY(id), " +
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
    public Cliente incluirCliente(Cliente cliente) {
        EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
        Endereco enderecoInserido = enderecoDAO.incluirEndereco(cliente.getEndereco());

        if (enderecoInserido != null) {
            String sqlCliente = "INSERT INTO CLIENTE (id, endereco_id, nome, email, telefone) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sqlCliente)) {
                stmt.setInt(1, cliente.getId());
                stmt.setLong(2, enderecoInserido.getId());
                stmt.setString(3, cliente.getNome());
                stmt.setString(4, cliente.getEmail());
                stmt.setString(5, cliente.getTelefone());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit();
                    System.out.println("Cliente inserido com sucesso!");
                    return cliente; // Retorna o cliente inserido
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } else {
            System.out.println("Erro ao inserir endereço. Cliente não inserido.");
        }
        return null; // Retorna null caso a inserção falhe
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome FROM CLIENTE";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                clientes.add(cliente); // Adiciona o cliente à lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes; // Retorna a lista de clientes
    }

    @Override
    public Cliente consultarClientePorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT c.id, c.nome, c.email, e.cidade, e.bairro, e.cep FROM CLIENTE c " +
                     "JOIN ENDERECO e ON c.endereco_id = e.id WHERE c.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                
                Endereco endereco = new Endereco(rs.getString("cidade"), rs.getString("bairro"), rs.getString("cep"));
                cliente.setEndereco(endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente; // Retorna o cliente encontrado ou null caso não seja encontrado
    }

    @Override
    public boolean atualizarCliente(Cliente cliente) {
        String sqlCliente = "UPDATE CLIENTE SET nome = ?, email = ?, telefone = ? WHERE id = ?";
        String sqlEndereco = "UPDATE ENDERECO SET cidade = ?, bairro = ?, cep = ? WHERE id = ?";

        try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente);
             PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco)) {

            stmtCliente.setString(1, cliente.getNome());
            stmtCliente.setString(2, cliente.getEmail());
            stmtCliente.setString(3, cliente.getTelefone());
            stmtCliente.setInt(4, cliente.getId());
            int rowsAffectedCliente = stmtCliente.executeUpdate();

            Endereco endereco = cliente.getEndereco();
            stmtEndereco.setString(1, endereco.getCidade());
            stmtEndereco.setString(2, endereco.getBairro());
            stmtEndereco.setString(3, endereco.getCep());
            stmtEndereco.setInt(4, endereco.getId());
            int rowsAffectedEndereco = stmtEndereco.executeUpdate();

            if (rowsAffectedCliente > 0 && rowsAffectedEndereco > 0) {
                conn.commit();
                return true; // Retorna true se a atualização for bem-sucedida
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false caso a atualização falhe
    }

    @Override
    public boolean apagarCliente(int id) {
        String sqlCliente = "DELETE FROM CLIENTE WHERE id = ?";
        String sqlEndereco = "DELETE FROM ENDERECO WHERE id = (SELECT endereco_id FROM CLIENTE WHERE id = ?)";

        try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente);
             PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco)) {

            stmtCliente.setInt(1, id);
            int rowsAffectedCliente = stmtCliente.executeUpdate();

            stmtEndereco.setInt(1, id);
            int rowsAffectedEndereco = stmtEndereco.executeUpdate();

            if (rowsAffectedCliente > 0 && rowsAffectedEndereco > 0) {
                conn.commit();
                return true; // Retorna true se o cliente for apagado com sucesso
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false caso a exclusão falhe
    }
}