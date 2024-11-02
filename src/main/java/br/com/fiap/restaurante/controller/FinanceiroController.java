package br.com.fiap.restaurante.controller;

import java.util.List;

import br.com.fiap.restaurante.exceptions.FinanceiroException;
import br.com.fiap.restaurante.model.EnumFinanceiro;
import br.com.fiap.restaurante.model.Financeiro;
import br.com.fiap.restaurante.model.FinanceiroDAOImpl;
import br.com.fiap.restaurante.services.FinanceiroService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/financeiro")
public class FinanceiroController {
    private final FinanceiroService financeiroService = new FinanceiroService(new FinanceiroDAOImpl());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFinanceiro() {
        List<Financeiro> transacoes = financeiroService.buscarTransacoes();
        return Response.ok(transacoes).build();
    }

    @GET
    @Path("/status/{codPedido}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarTransacao(@PathParam("codPedido") Integer codPedido) {
        try {
            Financeiro transacao = financeiroService.buscarTransacao(codPedido);
            return Response.ok(transacao).build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/pagamentoPresencial")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarTransacao(@QueryParam("valorPagamento") double valorPagamento, 
    		                           @QueryParam("codPedido")Integer codPedido, 
    		                           @QueryParam("metodoPagamento")String metodoPagamento,
    		                           @QueryParam("observacoes")String observacoes) {
        try {

            EnumFinanceiro.MetodoPagamento metodoPagamentoEnum = EnumFinanceiro.MetodoPagamento.fromCodigo(metodoPagamento);
            financeiroService.realizarPagamentoPresencial(valorPagamento, codPedido, metodoPagamentoEnum, observacoes);
            return Response.ok("Transação registrada com sucesso!").build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/aguardarDelivery")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aguardarPagamentoDelivery(@QueryParam("codPedido") Integer codPedido, 
    		                                  @QueryParam("metodoPagamento")String metodoPagamento, 
    		                                  @QueryParam("idCliente")Integer idCliente, 
    		                                  @QueryParam("observacoes") String observacoes) {
        try {
            EnumFinanceiro.MetodoPagamento metodoPagamentoEnum = EnumFinanceiro.MetodoPagamento.fromCodigo(metodoPagamento);
            financeiroService.aguardarPagamentoDelivery(codPedido, metodoPagamentoEnum, idCliente,observacoes);
            return Response.ok("Transação registrada com sucesso!").build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/finalizarDelivery")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response finalizarPagamentoDelivery(@QueryParam("valorPagamento") double valorPagamento, 
    											@QueryParam("codPedido") Integer codPedido, 
    											@QueryParam("observacoes")String observacoes) {
        try {
            financeiroService.finalizarPagamentoDelivery(valorPagamento, codPedido, observacoes);
            return Response.ok("Transação atualizada com sucesso.").build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/cancelarDelivery")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelarPagamentoDelivery(@QueryParam("codPedido") Integer codPedido, 
    											@QueryParam("observacoes")String observacoes) {
        try {
            financeiroService.cancelarPagamentoDelivery(codPedido, observacoes);
            return Response.ok("Transação atualizada com sucesso.").build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/concluidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUltimasTransacoesConcluidas() {
        List<Financeiro> transacoes = financeiroService.buscarUltimasTransacoesConcluidas();
        return Response.ok(transacoes).build();
    }

    @GET
    @Path("/pendentes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUltimasTransacoesPendentes() {
        List<Financeiro> transacoes = financeiroService.buscarUltimasTransacoesPendentes();
        return Response.ok(transacoes).build();
    }

    @GET
    @Path("/canceladas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUltimasTransacoesCanceladas() {
        List<Financeiro> transacoes = financeiroService.buscarUltimasTransacoesCanceladas();
        return Response.ok(transacoes).build();
    }

    @GET
    @Path("/deliveyPendentes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarDeliverysPendentes() {
        List<Financeiro> transacoes = financeiroService.buscarDeliverysPendentes();
        return Response.ok(transacoes).build();
    }

}
