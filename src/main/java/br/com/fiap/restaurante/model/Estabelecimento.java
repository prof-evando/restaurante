package br.com.fiap.restaurante.model;

public class Estabelecimento {
    
    /**
     * Campos
     */
    private String nome;
    private String tipo;
    private Endereco endereco;
    private String tel;
    
    /**
     * Construtor padrão
     */
    public Estabelecimento() {
    }
    
    /**
     * Construtor com parâmetros
     * @param endereco
     * @param tel
     */
    public Estabelecimento(String nome, String tipo, Endereco endereco, String tel) {
        this.nome = nome;
        this.tipo = tipo;
        this.endereco = endereco;
        this.tel = tel;
    }
    
    /**
     * GET E SET
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
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    /**
     * Apresenta o estabelecimento
     */
    public void apresentarEstabelecimento() {
        System.out.println("Nome do restaurante: " + this.nome + "\nTipo: " + this.tipo + "\nEndereço: " + this.endereco + "\nTel: " + this.tel);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
