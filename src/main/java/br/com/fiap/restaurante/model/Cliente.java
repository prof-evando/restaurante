package br.com.fiap.restaurante.model;

public class Cliente {
	
	/**
	 * Definindo campos da classe Cliente
	 */
	private String nome;
	private Endereco endereco;
	private String email; 

	
	/**
	 * Construtor
	 * @param nome
	 */
	public Cliente(String nome, String email, Endereco endereco) {
		super();
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
	}

	/**
	 * GET E SET
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * Apresenta informações sobre o Cliente
	 */
	public void apresentarCliente() {
		System.out.println("Cliente: " + this.nome + ", Endereço: " + this.getEndereco() + ", Email: "+ this.getEmail());
	}

	
	

}