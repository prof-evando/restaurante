package br.com.fiap.restaurante.model;

import br.com.fiap.restaurante.exceptions.FinanceiroException;
import br.com.fiap.restaurante.model.EnumFinanceiro;
import br.com.fiap.restaurante.model.Pedido_Financeiro;
import br.com.fiap.restaurante.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FinanceiroDAO {

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

    public void atualizarTrasacao(EnumFinanceiro.StatusPagamento status ,double valorPagamento, Pedido_Financeiro pedido, String observacoes) throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();
        String updateSQL = "UPDATE Transacao SET status_pagamento = ?, observacoes = ? WHERE id_pedido = ?";

        try(PreparedStatement stmt = conn.prepareStatement(updateSQL)){

            stmt.setString(1, status.getCodigo());
            stmt.setString(2, observacoes);
            stmt.setInt(3, pedido.getCod_pedido());

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


    public void registrarTransacao(Pedido_Financeiro pedido, String metodoPagamento,String statusPagamento,String tipoPedido, Integer idCliente, String observacoes) throws FinanceiroException {
        Connection conn = ConexaoBD.getInstance().getConn();

        String insertSQL = "INSERT INTO Transacao (id_pedido, valor_total, metodo_pagamento, status_pagamento, id_cliente, tipo_pedido, observacoes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setInt(1, pedido.getCod_pedido());
            stmt.setDouble(2, pedido.getValor_total());
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


    public void buscarTransacoes() throws FinanceiroException {
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

        try(PreparedStatement stmt = conn.prepareStatement(searchAllSQL)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idTransacao = rs.getInt("id_transacao");
                int idPedidoResult = rs.getInt("id_pedido");
                int idCliente = rs.getInt("id_cliente");
                String dataHora = rs.getString("data_hora");
                double valorTotal = rs.getDouble("valor_total");
                String metodoPagamento = rs.getString("metodo_pagamento");
                String statusPagamento = rs.getString("status_pagamento");
                String tipoPedido = rs.getString("tipo_pedido");
                String observacoes = rs.getString("observacoes");

                // Exibindo ou processando os resultados
                System.out.println("ID Transacao: " + idTransacao);
                System.out.println("ID Pedido: " + idPedidoResult);
                System.out.println("ID Cliente: " + idCliente);
                System.out.println("Data/Hora: " + dataHora);
                System.out.println("Valor Total: " + valorTotal);
                System.out.println("Metodo Pagamento: " + metodoPagamento);
                System.out.println("Status Pagamento: " + statusPagamento);
                System.out.println("Tipo Pedido: " + tipoPedido);
                System.out.println("Observacoes: " + observacoes);
                System.out.println("---------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }

    }

    public void buscarTransacaoPorPedido(Pedido_Financeiro pedido) throws FinanceiroException {
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
            stmt.setInt(1, pedido.getCod_pedido());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idTransacao = rs.getInt("id_transacao");
                int idPedidoResult = rs.getInt("id_pedido");
                int idCliente = rs.getInt("id_cliente");
                String dataHora = rs.getString("data_hora");
                double valorTotal = rs.getDouble("valor_total");
                String metodoPagamento = rs.getString("metodo_pagamento");
                String statusPagamento = rs.getString("status_pagamento");
                String tipoPedido = rs.getString("tipo_pedido");
                String observacoes = rs.getString("observacoes");

                // Exibindo ou processando os resultados
                System.out.println("ID Transacao: " + idTransacao);
                System.out.println("ID Pedido: " + idPedidoResult);
                System.out.println("ID Cliente: " + idCliente);
                System.out.println("Data/Hora: " + dataHora);
                System.out.println("Valor Total: " + valorTotal);
                System.out.println("Metodo Pagamento: " + metodoPagamento);
                System.out.println("Status Pagamento: " + statusPagamento);
                System.out.println("Tipo Pedido: " + tipoPedido);
                System.out.println("Observacoes: " + observacoes);
                System.out.println("---------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new FinanceiroException("Erro ao interagir com o banco de dados: " + e.getMessage());
        }

    }
}
