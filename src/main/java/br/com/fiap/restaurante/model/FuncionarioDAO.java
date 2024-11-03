package br.com.fiap.restaurante.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.restaurante.exceptions.FuncionarioException;
import br.com.fiap.restaurante.util.ConexaoBD;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO() {
        this.connection = ConexaoBD.getInstance().getConn(); 
    }

    public void cadastrar(Funcionario funcionario) throws FuncionarioException {
        String sql = "INSERT INTO funcionario (nome, cpf, cargo, salario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getCargo());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new FuncionarioException("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    public Funcionario consultarPorNome(String nome) throws FuncionarioException {
        String sql = "SELECT * FROM funcionario WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Funcionario(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                );
            } else {
                throw new FuncionarioException("Funcionário com nome " + nome + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new FuncionarioException("Erro ao consultar funcionário: " + e.getMessage());
        }
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                );
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }

    public void atualizar(Funcionario funcionario) throws FuncionarioException {
        String sql = "UPDATE funcionario SET cpf = ?, cargo = ?, salario = ? WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getCargo());
            stmt.setDouble(3, funcionario.getSalario());
            stmt.setString(4, funcionario.getNome());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new FuncionarioException("Funcionário com nome " + funcionario.getNome() + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new FuncionarioException("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }
}
