package br.com.fiap.restaurante.model;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
	
	public void criarTabelaCliente();
		
	public Cliente incluirCliente(Cliente cliente);
	
	public boolean apagarCliente(int id);
	
	public boolean atualizarCliente(Cliente cliente);
	
	public Cliente consultarClientePorId(int id);
	
	public List<Cliente> listarClientes();
	
}

