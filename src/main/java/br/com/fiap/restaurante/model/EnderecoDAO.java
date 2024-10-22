package br.com.fiap.restaurante.model;

public interface EnderecoDAO {
	
	public void criarSequenceEndereco();
	
	public void criarTabelaEndereco();
	
	public void criarTriggerEndereco();
	
	public Endereco incluirEndereco(Endereco endereco);
	
	
}
