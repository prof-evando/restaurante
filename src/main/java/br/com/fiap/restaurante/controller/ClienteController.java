package br.com.fiap.restaurante.controller;

import br.com.fiap.restaurante.model.Cliente;
import br.com.fiap.restaurante.model.ClienteDAOImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/clientes")
public class ClienteController {

    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();

    // Criação de um cliente
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response incluirCliente(Cliente cliente) {
        try {
            clienteDAO.incluirCliente(cliente);
            return Response.status(Response.Status.CREATED).entity(cliente).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao incluir cliente: " + e.getMessage()).build();
        }
    }

    // Listar todos os clientes
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarClientes() {
        try {
            List<Cliente> clientes = clienteDAO.listarClientes();
            return Response.ok(clientes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar clientes: " + e.getMessage()).build();
        }
    }

    // Consultar cliente por ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarClientePorId(@PathParam("id") int id) {
        try {
            Cliente cliente = clienteDAO.consultarClientePorId(id);
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
            }
            return Response.ok(cliente).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao consultar cliente: " + e.getMessage()).build();
        }
    }

    // Atualizar cliente
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarCliente(@PathParam("id") int id, Cliente clienteAtualizado) {
        try {
            Cliente clienteExistente = clienteDAO.consultarClientePorId(id);
            if (clienteExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
            }
            clienteAtualizado.setId(id);
            clienteDAO.atualizarCliente(clienteAtualizado);
            return Response.ok(clienteAtualizado).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar cliente: " + e.getMessage()).build();
        }
    }

    // Apagar cliente por ID
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response apagarCliente(@PathParam("id") int id) {
        try {
            clienteDAO.apagarCliente(id);
            return Response.ok("Cliente apagado com sucesso").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao apagar cliente: " + e.getMessage()).build();
        }
    }
}
