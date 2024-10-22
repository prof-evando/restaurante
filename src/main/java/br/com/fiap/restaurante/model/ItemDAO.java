package br.com.fiap.restaurante.model;

public interface ItemDAO {
	
	public void adicionar(Item item);
	
	public Item obterPorId(int id);
	
	public void atualizar(Item item);
	
	public void apagar(int id);

}
