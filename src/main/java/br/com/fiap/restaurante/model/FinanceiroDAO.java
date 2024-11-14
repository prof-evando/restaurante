package br.com.fiap.restaurante.model;

import br.com.fiap.restaurante.exceptions.FinanceiroException;

import java.util.List;


public interface FinanceiroDAO {

    String buscarStatusTransacao(int codPedido) throws FinanceiroException;
    void atualizarTrasacao(EnumFinanceiro.StatusPagamento status, Integer codPedido, String observacoes) throws FinanceiroException;
    void registrarTransacao(Integer idPedido, double valorPagamento , String metodoPagamento, String statusPagamento, String tipoPedido, Integer idCliente, String observacoes) throws FinanceiroException;
    List<Financeiro> buscarTransacoes(String status) throws FinanceiroException;
    Financeiro buscarTransacaoPorPedido(Integer pedido) throws FinanceiroException;
}
