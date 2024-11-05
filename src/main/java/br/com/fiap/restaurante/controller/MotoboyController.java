package br.com.fiap.restaurante.controller;

import br.com.fiap.restaurante.model.Motoboy;
import br.com.fiap.restaurante.model.MotoboyDAOImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/motoboys")
public class MotoboyController {
	
	private MotoboyDAOImpl motoboyDAO = new MotoboyDAOImpl();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarMotoboy(Motoboy motoboy) {
		try {
			motoboyDAO.incluirMotoboy(motoboy);
			return Response.status(Response.Status.CREATED).entity(motoboy).build();
		} catch(Exception e) {
			 return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar Motoboy").build();
		}
	}
}
