package br.com.fiap.restaurante.model;

public class Motoboy {

    /**
     * Definindo campos da classe Motoboy
     */
    private long id;
    private String nome;
    private String veiculo;
    private boolean ehOcupado;

    /**
     * Construtor padrão
     */
    public Motoboy() {
    }

    /**
     * Construtor com parâmetros
     * @param nome
     * @param veiculo
     * @param ehOcupado
     */
    public Motoboy(String nome, String veiculo, boolean ehOcupado) {
        this.nome = nome;
        this.veiculo = veiculo;
        this.ehOcupado = ehOcupado;
    }

    /**
     * GET e SET
     */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public boolean getEhOcupado() {
        return ehOcupado;
    }

    public void setEhOcupado(boolean ehOcupado) {
        this.ehOcupado = ehOcupado;
    }

    /**
     * Apresenta informações sobre o Motoboy
     */
    public void apresentarMotoboy() {
        System.out.println(this.nome + ", " + this.veiculo + ", está ocupado? " + this.ehOcupado);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
