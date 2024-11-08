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
	
}
