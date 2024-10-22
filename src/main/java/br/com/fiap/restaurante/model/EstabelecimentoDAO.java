package br.com.fiap.restaurante.model;

public interface EstabelecimentoDAO {
	
	public void criarTabelaEstabelecimento();
	
	public void incluirEstabelecimento(Endereco enderecoID, Estabelecimento estabelecimento);
}
