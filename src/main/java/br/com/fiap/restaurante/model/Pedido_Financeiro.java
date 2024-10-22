package br.com.fiap.restaurante.model;

public class Pedido_Financeiro {

    private int cod_pedido;
    private double valor_total;

    public Pedido_Financeiro(int cod_pedido, double valor_total) {
        this.cod_pedido = cod_pedido;
        this.valor_total = valor_total;
    }

    public int getCod_pedido() {
        return cod_pedido;
    }

    public double getValor_total() {
        return valor_total;
    }

}
