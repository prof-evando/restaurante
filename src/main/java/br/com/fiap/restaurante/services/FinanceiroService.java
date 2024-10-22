package br.com.fiap.restaurante.services;

import br.com.fiap.restaurante.exceptions.FinanceiroException;
import br.com.fiap.restaurante.model.EnumFinanceiro;
import br.com.fiap.restaurante.model.FinanceiroDAO;
import br.com.fiap.restaurante.model.Pedido_Financeiro;
import br.com.fiap.restaurante.util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinanceiroService {
    private FinanceiroDAO financeiroDAO;

    public FinanceiroService() {
        financeiroDAO = new FinanceiroDAO();
    }

    public void realizarPagamentoPresencial(double valorPagamento, Pedido_Financeiro pedido,EnumFinanceiro.MetodoPagamento metodoPagamentoEnum, String observacoes) throws FinanceiroException {

        if (valorPagamento < 0) {
            throw new FinanceiroException("O valor do pagamento não pode ser negativo.");
        }
        double troco = valorPagamento - pedido.getValor_total();

        if (troco < 0) {
            throw new FinanceiroException("Valor insuficiente para realizar o pagamento. Está faltando: R$" + troco*-1);
        }

        System.out.println("Pagamento realizado com sucesso! Troco: " + troco);

        String statusPagamento = EnumFinanceiro.StatusPagamento.PENDENTE.getCodigo();
        String tipoPedido = EnumFinanceiro.TipoPedido.PRESENCIAL.getCodigo();
        String metodoPagamento = metodoPagamentoEnum.getCodigo();

        financeiroDAO.registrarTransacao(pedido, metodoPagamento, statusPagamento, tipoPedido, null, observacoes);
    }

    public void aguardarPagamentoDelivery(Pedido_Financeiro pedido, EnumFinanceiro.MetodoPagamento metodoPagamentoEnum, Integer idCliente, String observacoes) throws FinanceiroException {

        if (idCliente == null) {
            throw new FinanceiroException("ID do cliente é obrigatório para pedidos do tipo 'Delivery'.");
        }
        System.out.println("Pagamento realizado para o cliente com ID: " + idCliente);


        String tipoPedido = EnumFinanceiro.TipoPedido.DELIVERY.getCodigo();
        String statusPagamento = EnumFinanceiro.StatusPagamento.PENDENTE.getCodigo();
        String metodoPagamento = metodoPagamentoEnum.getCodigo();

        financeiroDAO.registrarTransacao(pedido, metodoPagamento, statusPagamento, tipoPedido, idCliente, observacoes);
    }

    public void finalizarPagamentoDelivery(double valorPagamento, Pedido_Financeiro pedido, String observacoes) throws FinanceiroException {

        String statusAtual = financeiroDAO.buscarStatusTransacao(pedido.getCod_pedido());
        EnumFinanceiro.StatusPagamento novoStatus = EnumFinanceiro.StatusPagamento.CONCLUIDO;

        verificarMudancaDeStatus(financeiroDAO.buscarStatusTransacao(pedido.getCod_pedido()),novoStatus);

        financeiroDAO.atualizarTrasacao(EnumFinanceiro.StatusPagamento.CONCLUIDO, valorPagamento, pedido, observacoes);
    }

    public void cancelarPagamentoDelivery(Pedido_Financeiro pedido, String observacoes) throws FinanceiroException {

        String statusAtual = financeiroDAO.buscarStatusTransacao(pedido.getCod_pedido());
        EnumFinanceiro.StatusPagamento novoStatus = EnumFinanceiro.StatusPagamento.CANCELADO;

        verificarMudancaDeStatus(financeiroDAO.buscarStatusTransacao(pedido.getCod_pedido()),novoStatus);

        financeiroDAO.atualizarTrasacao(EnumFinanceiro.StatusPagamento.CANCELADO,0, pedido, observacoes);
    }

    public void verificarMudancaDeStatus(String statusAtual, EnumFinanceiro.StatusPagamento novoStatus) throws FinanceiroException {
        if ((statusAtual.equals(EnumFinanceiro.StatusPagamento.CONCLUIDO.getCodigo()) && novoStatus.equals(EnumFinanceiro.StatusPagamento.CANCELADO)) ||
                (statusAtual.equals(EnumFinanceiro.StatusPagamento.CANCELADO.getCodigo()) && novoStatus.equals(EnumFinanceiro.StatusPagamento.CONCLUIDO))) {
            throw new FinanceiroException("Não é permitido alterar o status de 'Concluído' para 'Cancelado' ou vice-versa.");
        }
    }

}
