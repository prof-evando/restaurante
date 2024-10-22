package br.com.fiap.restaurante.model;

public interface PedidoDAO {
	
	public void adicionar(Pedido pedido);
	
	public void atualizar(Pedido pedido);
	
	public void apagar(int id);
	
	public Pedido listarPedido(int numero);
}
