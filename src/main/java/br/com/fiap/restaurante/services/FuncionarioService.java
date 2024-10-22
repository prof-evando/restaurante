package br.com.fiap.restaurante.services;

import java.util.List;

import br.com.fiap.restaurante.exceptions.FuncionarioException;
import br.com.fiap.restaurante.model.Funcionario;
import br.com.fiap.restaurante.model.FuncionarioDAO;

public class FuncionarioService {
    private FuncionarioDAO funcionarioDAO;

    public FuncionarioService() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

     public void cadastrarFuncionario(String nome, String cpf, String cargo, double salario) throws FuncionarioException {
         if (nome == null || nome.trim().isEmpty()) {
            throw new FuncionarioException("Nome do funcionário é obrigatório.");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new FuncionarioException("CPF do funcionário é obrigatório.");
        }

         Funcionario funcionario = new Funcionario(nome, cpf, cargo, salario);

        funcionarioDAO.cadastrar(funcionario);
    }

     public Funcionario consultarFuncionarioPorNome(String nome) throws FuncionarioException {
        return funcionarioDAO.consultarPorNome(nome);
    }

     public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioDAO.listarTodos();
    }

     public void atualizarDadosFuncionario(Funcionario funcionario) throws FuncionarioException {
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new FuncionarioException("Nome do funcionário é obrigatório para a atualização.");
        }

        funcionarioDAO.atualizar(funcionario);
    }
}
