package br.com.fiap.restaurante.controller;

import br.com.fiap.restaurante.model.Funcionario;
import br.com.fiap.restaurante.model.FuncionarioDAO;
import br.com.fiap.restaurante.service.FuncionarioService;
import br.com.fiap.restaurante.exceptions.FuncionarioException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/funcionarios") 
public class FuncionarioController {

    private FuncionarioService service = new FuncionarioService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarFuncionarios() {
        List<Funcionario> funcionarios = service.listarTodosFuncionarios();
        if (funcionarios.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum funcionário cadastrado.").build();
        }
        return Response.ok(funcionarios).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarFuncionario(Funcionario funcionario) {
        try {
            service.cadastrarFuncionario(funcionario.getNome(), funcionario.getCpf(), funcionario.getCargo(), funcionario.getSalario());
            return Response.status(Response.Status.CREATED).entity("Funcionário cadastrado com sucesso.").build();
        } catch (FuncionarioException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("/{nome}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarFuncionario(@PathParam("nome") String nome) {
        try {
            Funcionario funcionario = service.consultarFuncionarioPorNome(nome);
            return Response.ok(funcionario).build();
        } catch (FuncionarioException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarFuncionario(Funcionario funcionario) {
        try {
            service.atualizarDadosFuncionario(funcionario);
            return Response.ok("Funcionário atualizado com sucesso.").build();
        } catch (FuncionarioException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
