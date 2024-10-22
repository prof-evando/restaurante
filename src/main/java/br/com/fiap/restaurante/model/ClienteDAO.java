package br.com.fiap.restaurante.model;

public interface ClienteDAO {
	
	public void criarTabelaCliente();
		
	public void incluirCliente(Endereco enderecoID, String nome, String Email);
}

