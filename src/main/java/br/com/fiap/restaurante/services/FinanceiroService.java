package br.com.fiap.restaurante.services;

import br.com.fiap.restaurante.exceptions.FinanceiroException;
import br.com.fiap.restaurante.model.*;

import java.util.List;

public class FinanceiroService {
    private final FinanceiroDAO financeiroDAO;
    private final PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();

    public FinanceiroService(FinanceiroDAO financeiroDAO) {
        this.financeiroDAO = financeiroDAO;
    }

    public void realizarPagamentoPresencial(double valorPagamento, Integer codPedido, EnumFinanceiro.MetodoPagamento metodoPagamentoEnum, String observacoes) throws FinanceiroException {

        Pedido pedido = pedidoDAO.listarPedido(codPedido);

        if (valorPagamento < 0) {
            throw new FinanceiroException("O valor do pagamento não pode ser negativo.");
        }
        double troco = valorPagamento - pedido.getValorTotal();

        if (troco < 0) {
            throw new FinanceiroException("Valor insuficiente para realizar o pagamento. Está faltando: R$" + troco*-1);
        }

        String statusPagamento = EnumFinanceiro.StatusPagamento.PENDENTE.getCodigo();
        String tipoPedido = EnumFinanceiro.TipoPedido.PRESENCIAL.getCodigo();
        String metodoPagamento = metodoPagamentoEnum.getCodigo();

        financeiroDAO.registrarTransacao(pedido, metodoPagamento, statusPagamento, tipoPedido, null, observacoes);
    }

    public void aguardarPagamentoDelivery(Integer codPedido, EnumFinanceiro.MetodoPagamento metodoPagamentoEnum, Integer idCliente, String observacoes) throws FinanceiroException {

        if (idCliente == null) {
            throw new FinanceiroException("ID do cliente é obrigatório para pedidos do tipo 'Delivery'.");
        }
        Pedido pedido = pedidoDAO.listarPedido(codPedido);

        String tipoPedido = EnumFinanceiro.TipoPedido.DELIVERY.getCodigo();
        String statusPagamento = EnumFinanceiro.StatusPagamento.PENDENTE.getCodigo();
        String metodoPagamento = metodoPagamentoEnum.getCodigo();

        financeiroDAO.registrarTransacao(pedido, metodoPagamento, statusPagamento, tipoPedido, idCliente, observacoes);
    }

    public void finalizarPagamentoDelivery(double valorPagamento, Integer codPedido, String observacoes) throws FinanceiroException {

        Pedido pedido = pedidoDAO.listarPedido(codPedido);
        String statusAtual = financeiroDAO.buscarStatusTransacao(pedido.getNumero());
        EnumFinanceiro.StatusPagamento novoStatus = EnumFinanceiro.StatusPagamento.CONCLUIDO;

        verificarMudancaDeStatus(statusAtual,novoStatus);

        financeiroDAO.atualizarTrasacao(EnumFinanceiro.StatusPagamento.CONCLUIDO, valorPagamento, pedido, observacoes);
    }

    public void cancelarPagamentoDelivery(Integer codPedido, String observacoes) throws FinanceiroException {

        Pedido pedido = pedidoDAO.listarPedido(codPedido);
        String statusAtual = financeiroDAO.buscarStatusTransacao(pedido.getNumero());
        EnumFinanceiro.StatusPagamento novoStatus = EnumFinanceiro.StatusPagamento.CANCELADO;

        verificarMudancaDeStatus(statusAtual,novoStatus);

        financeiroDAO.atualizarTrasacao(EnumFinanceiro.StatusPagamento.CANCELADO,0, pedido, observacoes);
    }

    public void verificarMudancaDeStatus(String statusAtual, EnumFinanceiro.StatusPagamento novoStatus) throws FinanceiroException {
        if ((statusAtual.equals(EnumFinanceiro.StatusPagamento.CONCLUIDO.getCodigo()) && novoStatus.equals(EnumFinanceiro.StatusPagamento.CANCELADO)) ||
                (statusAtual.equals(EnumFinanceiro.StatusPagamento.CANCELADO.getCodigo()) && novoStatus.equals(EnumFinanceiro.StatusPagamento.CONCLUIDO))) {
            throw new FinanceiroException("Não é permitido alterar o status de 'Concluído' para 'Cancelado' ou vice-versa.");
        }
    }

    public List<Financeiro> buscarTransacoes(){
        try {
            return financeiroDAO.buscarTransacoes();
        } catch (FinanceiroException e) {
            throw new RuntimeException(e);
        }
    }

    public Financeiro buscarTransacao(Integer codPedido) throws FinanceiroException {
        try{
            Pedido pedido = pedidoDAO.listarPedido(codPedido);
            return financeiroDAO.buscarTransacaoPorPedido(pedido);
        } catch (FinanceiroException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Financeiro> buscarUltimasTransacoesConcluidas(){
        try{
            return financeiroDAO.buscarUltimasTransacoes(EnumFinanceiro.StatusPagamento.CONCLUIDO.getCodigo());
        } catch (FinanceiroException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Financeiro> buscarUltimasTransacoesPendentes(){
        try{
            return financeiroDAO.buscarUltimasTransacoes(EnumFinanceiro.StatusPagamento.PENDENTE.getCodigo());
        } catch (FinanceiroException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Financeiro> buscarUltimasTransacoesCanceladas(){
        try{
            return financeiroDAO.buscarUltimasTransacoes(EnumFinanceiro.StatusPagamento.CANCELADO.getCodigo());
        } catch (FinanceiroException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Financeiro> buscarDeliverysPendentes(){
        try{
            return financeiroDAO.buscarDeliverysPendentes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
