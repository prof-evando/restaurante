package br.com.fiap.restaurante.controller;

import br.com.fiap.restaurante.model.Estabelecimento;
import br.com.fiap.restaurante.model.EstabelecimentoDAOImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/estabelecimentos")
public class EstabelecimentoController {
	
	private EstabelecimentoDAOImpl estabelecimentoDAO = new EstabelecimentoDAOImpl();
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarEstabelecimento(Estabelecimento estabelecimento) {
        try {
            estabelecimentoDAO.incluirEstabelecimento(estabelecimento.getEndereco(), estabelecimento);
            return Response.status(Response.Status.CREATED).entity(estabelecimento).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar o estabelecimento").build();
        }
    }

}

