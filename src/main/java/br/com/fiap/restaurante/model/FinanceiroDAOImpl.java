package br.com.fiap.restaurante.model;

import br.com.fiap.restaurante.exceptions.FinanceiroException;
import br.com.fiap.restaurante.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinanceiroDAOImpl implements FinanceiroDAO {

    @Override
    public String buscarStatusTransacao(int codPedido) throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();
        String selectSQL = "SELECT status_pagamento FROM Transacao WHERE id_pedido = ?";

        try (PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, codPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("status_pagamento");
            } else {
                throw new FinanceiroException("Nenhuma transação encontrada com o ID fornecido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao consultar o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void atualizarTrasacao(EnumFinanceiro.StatusPagamento status ,double valorPagamento, Pedido pedido, String observacoes) throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();
        String updateSQL = "UPDATE Transacao SET status_pagamento = ?, observacoes = ? WHERE id_pedido = ?";

        try(PreparedStatement stmt = conn.prepareStatement(updateSQL)){

            stmt.setString(1, status.getCodigo());
            stmt.setString(2, observacoes);
            stmt.setInt(3, pedido.getNumero());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Transação atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma transação encontrada com o ID fornecido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }
    }


    @Override
    public void registrarTransacao(Pedido pedido, String metodoPagamento,String statusPagamento,String tipoPedido, Integer idCliente, String observacoes) throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();

        String insertSQL = "INSERT INTO Transacao (id_pedido, valor_total, metodo_pagamento, status_pagamento, id_cliente, tipo_pedido, observacoes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setInt(1, pedido.getNumero());
            stmt.setDouble(2, pedido.getValorTotal());
            stmt.setString(3, metodoPagamento);
            stmt.setString(4, statusPagamento);
            stmt.setInt(5, idCliente);
            stmt.setString(6, tipoPedido);
            stmt.setString(7, observacoes);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Transação registrada com sucesso!");
            } else {
                throw new FinanceiroException("Falha ao registrar a transação.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }

    }

    @Override
    public List<Financeiro> buscarTransacoes() throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();
        String searchAllSQL = "SELECT T.id_transacao, T.id_pedido, T.id_cliente, " +
                "TO_CHAR(T.data_hora, 'YYYY-MM-DD HH24:MI') AS data_hora, " +
                "T.valor_total, MP.descricao AS metodo_pagamento, " +
                "SP.descricao AS status_pagamento, " +
                "TP.descricao AS tipo_pedido, T.observacoes " +
                "FROM Transacao T " +
                "INNER JOIN metodoPagamento MP ON T.metodo_pagamento = MP.codigo " +
                "INNER JOIN statusPagamento SP ON T.status_pagamento = SP.codigo " +
                "INNER JOIN TipoPedido TP ON T.tipo_pedido = TP.codigo " +
                "ORDER BY T.data_hora DESC";
        ;

        List<Financeiro> transacoes = new ArrayList();

        try(PreparedStatement stmt = conn.prepareStatement(searchAllSQL)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Financeiro transacao = new Financeiro(
                        rs.getInt("id_transacao"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getString("data_hora"),
                        rs.getDouble("valor_total"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("status_pagamento"),
                        rs.getString("tipo_pedido"),
                        rs.getString("observacoes")
                );
                transacoes.add(transacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }
        return transacoes;
    }

    @Override
    public Financeiro buscarTransacaoPorPedido(Pedido pedido) throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();
        String searchSQL = "SELECT T.id_transacao, T.id_pedido, T.id_cliente, " +
                "TO_CHAR(T.data_hora, 'YYYY-MM-DD HH24:MI') AS data_hora, " +
                "T.valor_total, MP.descricao AS metodo_pagamento, " +
                "SP.descricao AS status_pagamento, " +
                "TP.descricao AS tipo_pedido, T.observacoes " +
                "FROM Transacao T " +
                "INNER JOIN metodoPagamento MP ON T.metodo_pagamento = MP.codigo " +
                "INNER JOIN statusPagamento SP ON T.status_pagamento = SP.codigo " +
                "INNER JOIN TipoPedido TP ON T.tipo_pedido = TP.codigo " +
                "WHERE T.id_pedido = ?";

        try(PreparedStatement stmt = conn.prepareStatement(searchSQL)){
            stmt.setInt(1, pedido.getNumero());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Financeiro transacao = new Financeiro(
                        rs.getInt("id_transacao"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getString("data_hora"),
                        rs.getDouble("valor_total"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("status_pagamento"),
                        rs.getString("tipo_pedido"),
                        rs.getString("observacoes")
                );
                return transacao;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Financeiro> buscarUltimasTransacoes(String status) throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();
        String searchSQL = "SELECT * FROM ( " +
                "    SELECT " +
                "        T.id_transacao, " +
                "        T.id_pedido, " +
                "        T.id_cliente, " +
                "        TO_CHAR(T.data_hora, 'YYYY-MM-DD HH24:MI') AS data_hora, " +
                "        T.valor_total, " +
                "        MP.descricao AS metodo_pagamento, " +
                "        SP.descricao AS status_pagamento, " +
                "        TP.descricao AS tipo_pedido, " +
                "        T.observacoes " +
                "    FROM Transacao T " +
                "        INNER JOIN metodoPagamento MP ON T.metodo_pagamento = MP.codigo " +
                "        INNER JOIN statusPagamento SP ON T.status_pagamento = SP.codigo " +
                "        INNER JOIN TipoPedido TP ON T.tipo_pedido = TP.codigo " +
                "    WHERE T.status_pagamento = ?" +
                "    ORDER BY T.data_hora DESC " +
                ") " +
                "WHERE ROWNUM <= 25";


        List<Financeiro> transacoes = new ArrayList();

        try(PreparedStatement stmt = conn.prepareStatement(searchSQL)){
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Financeiro transacao = new Financeiro(
                        rs.getInt("id_transacao"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getString("data_hora"),
                        rs.getDouble("valor_total"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("status_pagamento"),
                        rs.getString("tipo_pedido"),
                        rs.getString("observacoes")
                );
                transacoes.add(transacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }
        return transacoes;
    }

    @Override
    public List<Financeiro> buscarDeliverysPendentes() throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();
        String searchSQL = "SELECT T.id_transacao, T.id_pedido, T.id_cliente, " +
                "TO_CHAR(T.data_hora, 'YYYY-MM-DD HH24:MI') AS data_hora, " +
                "T.valor_total, MP.descricao AS metodo_pagamento, " +
                "SP.descricao AS status_pagamento, " +
                "TP.descricao AS tipo_pedido, T.observacoes " +
                "FROM Transacao T " +
                "INNER JOIN metodoPagamento MP ON T.metodo_pagamento = MP.codigo " +
                "INNER JOIN statusPagamento SP ON T.status_pagamento = SP.codigo " +
                "INNER JOIN TipoPedido TP ON T.tipo_pedido = TP.codigo " +
                "WHERE T.tipo_pedido = 'D' AND T.status_pagamento = 'P'";

        List<Financeiro> transacoes = new ArrayList();

        try(PreparedStatement stmt = conn.prepareStatement(searchSQL)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Financeiro transacao = new Financeiro(
                        rs.getInt("id_transacao"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getString("data_hora"),
                        rs.getDouble("valor_total"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("status_pagamento"),
                        rs.getString("tipo_pedido"),
                        rs.getString("observacoes")
                );
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }
        return transacoes;
    }


}
