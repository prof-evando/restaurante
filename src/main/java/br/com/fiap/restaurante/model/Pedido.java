package br.com.fiap.restaurante.model;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.restaurante.exceptions.PedidoException;

public class Pedido {
	private int numero;
	private String descricao;
	private double valorTotal;
	private boolean pago;
	private List<Item> itens = new ArrayList<>();
	private Estabelecimento estabelecimento;
	private Cliente cliente;
	private Motoboy motoboy;

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Motoboy getMotoboy() {
		return motoboy;
	}

	public void setMotoboy(Motoboy motoboy) {
		this.motoboy = motoboy;
	}

	public Pedido() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pedido(int numero) {
		super();
		this.numero = numero;
	}
	
	public Pedido(int numero, String descricao, double valorTotal) {
		super();
		this.numero = numero;
		this.descricao = descricao;
		this.valorTotal = valorTotal;
	}

	public Pedido(int numero, String descricao, double valorTotal, boolean pago) {
		super();
		this.numero = numero;
		this.descricao = descricao;
		this.valorTotal = valorTotal;
		this.pago = pago;
	}

	public Pedido(int numero, String descricao, double valorTotal, boolean pago, List<Item> itens) {
		super();
		this.numero = numero;
		this.descricao = descricao;
		this.valorTotal = valorTotal;
		this.pago = pago;
		this.itens = itens;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int id) {
		this.numero = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public void adicionarItem(Item item) {
		itens.add(item);
	}

	public void pagar() throws PedidoException {
		if (pago) {
			throw new PedidoException("O pedido já está pago.");
		}
		this.pago = true;
	}
	
	public void somarValor(List<Item> itens, Pedido pedido) throws PedidoException{
		double valorTotal = 0;
		for (Item item: itens) {
			valorTotal += (item.getPrecoUnitario() * item.getQuantidade() );
		}
		if (valorTotal == 0) {
			throw new PedidoException("Não é possível fechar uma conta que totalize zero reais.");
		}
		pedido.setValorTotal(valorTotal);
	}

	@Override
	public String toString() {
		return "Pedido [id=" + this.numero + ", descricao=" + this.descricao + ", valorTotal=" + this.valorTotal + ", pago=" + this.pago
				+ ", itens=" + this.itens.toString() + "]";
	}

}
