package br.com.fiap.restaurante.model;

public class Endereco{

    /**
     * Campos da classe endereço
     */
	
	private int id; 
    private String cidade;
    private String bairro;
    private String cep;

    /**
     * Construtor
     * @param cidade
     * @param bairro
     * @param cep
     */
    public Endereco(String cidade, String bairro, String cep) {
        super();
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
    }

    /**
     * GET E SET
     * @return
     */
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Sobrescreve o método toString para apresentar o endereço corretamente
     */
    @Override
    public String toString() {
        return "Cidade: " + this.cidade + ", Bairro: " + this.bairro + ", CEP: " + this.cep;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
