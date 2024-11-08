package br.com.fiap.restaurante.controller;

import java.time.LocalDateTime;
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

@Path("/api/v1/financeiros")
public class FinanceiroController {
    private final FinanceiroService financeiroService = new FinanceiroService(new FinanceiroDAOImpl());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFinanceiro(@QueryParam("status") String status) {
        List<Financeiro> transacoes = financeiroService.buscarTransacoes(status);
        return Response.ok(transacoes).build();
    }

    @GET
    @Path("/{codPedido}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response bucaPedido(@PathParam("codPedido") Integer codPedido) {
        try {
            Financeiro transacao = financeiroService.buscarTransacao(codPedido);
            return Response.ok(transacao).build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarTransacao(@QueryParam("tipoPagamento") String tipoPagamento, Financeiro financeiro) {
    	 financeiro.setDataHora(LocalDateTime.now().toString());
        try {
        	financeiroService.realizarPagamento(financeiro, tipoPagamento);
            return Response.ok("Transação registrada com sucesso!").build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{codPedido}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizaDelivery(@PathParam("codPedido") Integer codPedido, Financeiro financeiro) {
        try {
        	financeiroService.atualizarDelivery(financeiro.getStatusPagamento(),codPedido, financeiro.getObservacoes());
            return Response.accepted().build();
        } catch (FinanceiroException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(e.getMessage()).build();
        }
    }
    
}
