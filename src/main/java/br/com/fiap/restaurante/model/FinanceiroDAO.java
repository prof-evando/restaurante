package br.com.fiap.restaurante.model;

import br.com.fiap.restaurante.exceptions.FinanceiroException;

import java.util.List;


public interface FinanceiroDAO {

    String buscarStatusTransacao(int codPedido) throws FinanceiroException;
    void atualizarTrasacao(EnumFinanceiro.StatusPagamento status, double valorPagamento, Pedido pedido, String observacoes) throws FinanceiroException;
    void registrarTransacao(Pedido pedido, String metodoPagamento, String statusPagamento, String tipoPedido, Integer idCliente, String observacoes) throws FinanceiroException;
    List<Financeiro> buscarTransacoes() throws FinanceiroException;
    Financeiro buscarTransacaoPorPedido(Pedido pedido) throws FinanceiroException;
    List<Financeiro> buscarUltimasTransacoes(String status) throws FinanceiroException;
    List<Financeiro> buscarDeliverysPendentes() throws FinanceiroException;
}
