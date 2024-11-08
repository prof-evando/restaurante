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
    
    public void realizarPagamento(Financeiro financeiro, String tipoPagamento) throws FinanceiroException {
    	
    	
        Pedido pedido = new Pedido(financeiro.getIdPedido(),financeiro.getObservacoes(), financeiro.getValorPagamento()); 
    	
        
        if(tipoPagamento.contains("P")) {

            if (financeiro.getValorPagamento() < 0) {
                throw new FinanceiroException("O valor do pagamento não pode ser negativo.");
            }
            double troco = financeiro.getValorPagamento() - pedido.getValorTotal();

            if (troco < 0) {
                throw new FinanceiroException("Valor insuficiente para realizar o pagamento. Está faltando: R$" + troco*-1);
            }

            String statusPagamento = EnumFinanceiro.StatusPagamento.CONCLUIDO.getCodigo();
            String metodoPagamento = EnumFinanceiro.MetodoPagamento.fromCodigo(financeiro.getMetodoPagamento()).getCodigo();

            financeiroDAO.registrarTransacao(pedido.getNumero(), financeiro.getValorPagamento() , metodoPagamento, statusPagamento, tipoPagamento, 0, financeiro.getObservacoes());
        }
        
        else if(tipoPagamento.contains("D")) {
        	if (financeiro.getIdCliente() == null) {
                throw new FinanceiroException("ID do cliente é obrigatório para pedidos do tipo 'Delivery'.");
            }
        	
            String statusPagamento = EnumFinanceiro.StatusPagamento.PENDENTE.getCodigo();
            String metodoPagamento = EnumFinanceiro.MetodoPagamento.fromCodigo(financeiro.getMetodoPagamento()).getCodigo();

            financeiroDAO.registrarTransacao(pedido.getNumero(), pedido.getValorTotal(), metodoPagamento, statusPagamento, tipoPagamento, financeiro.getIdCliente(), financeiro.getObservacoes());
        }

    }

    public void atualizarDelivery(String status,Integer codPedido, String observacoes) throws FinanceiroException {
    	try {
           String statusAtual = financeiroDAO.buscarStatusTransacao(codPedido);
           EnumFinanceiro.StatusPagamento novoStatus = EnumFinanceiro.StatusPagamento.fromCodigo(status);
      	
          verificarMudancaDeStatus(statusAtual,novoStatus);

          financeiroDAO.atualizarTrasacao(novoStatus, codPedido, observacoes);
    	}catch(FinanceiroException e) {
    		throw new RuntimeException(e);
    	}
  }


    public void verificarMudancaDeStatus(String statusAtual, EnumFinanceiro.StatusPagamento novoStatus) throws FinanceiroException {
        if ((statusAtual.equals(EnumFinanceiro.StatusPagamento.CONCLUIDO.getCodigo()) && novoStatus.equals(EnumFinanceiro.StatusPagamento.CANCELADO)) ||
                (statusAtual.equals(EnumFinanceiro.StatusPagamento.CANCELADO.getCodigo()) && novoStatus.equals(EnumFinanceiro.StatusPagamento.CONCLUIDO))) {
            throw new FinanceiroException("Não é permitido alterar o status de 'Concluído' para 'Cancelado' ou vice-versa.");
        }
    }

    public List<Financeiro> buscarTransacoes(String status){
        try {
            return financeiroDAO.buscarTransacoes(status);
        } catch (FinanceiroException e) {
            throw new RuntimeException(e);
        }
    }

    public Financeiro buscarTransacao(Integer codPedido) throws FinanceiroException {
        try{
            return financeiroDAO.buscarTransacaoPorPedido(codPedido);
        } catch (FinanceiroException e) {
            throw new RuntimeException(e);
        }
    }


}
