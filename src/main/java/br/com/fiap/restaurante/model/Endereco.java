package br.com.fiap.restaurante.model;

public class Endereco {

    /**
     * Campos da classe endereço
     */
    private int id; 
    private String cidade;
    private String bairro;
    private String cep;

    /**
     * Construtor padrão
     */
    public Endereco() {
    }

    /**
     * Construtor com parâmetros
     * @param cidade
     * @param bairro
     * @param cep
     */
    public Endereco(String cidade, String bairro, String cep) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
    }

    /**
     * GET e SET
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sobrescreve o método toString para apresentar o endereço corretamente
     */
    @Override
    public String toString() {
        return "Cidade: " + this.cidade + ", Bairro: " + this.bairro + ", CEP: " + this.cep;
    }
}
