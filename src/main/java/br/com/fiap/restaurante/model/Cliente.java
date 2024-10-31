package br.com.fiap.restaurante.model;

public class Cliente {
	
	private int id;  // Campo ID adicionado
	private String nome;
	private String email; 
	private String telefone; // Novo campo telefone
	private Endereco endereco;

	public Cliente(int id, String nome, String email, String telefone, Endereco endereco) {
		this.id = id; 
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	// Método para apresentação do cliente
	public void apresentarCliente() {
		System.out.println("ID: " + this.id + " | Cliente: " + this.nome + " | Email: "+ this.email + " | Telefone: " + this.telefone + " | Endereço: " + this.getEndereco());
	}
}
