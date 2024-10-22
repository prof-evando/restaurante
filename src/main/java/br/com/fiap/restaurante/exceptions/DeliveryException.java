package br.com.fiap.restaurante.exceptions;

public class DeliveryException extends Exception {
    public DeliveryException(String mensagem) {
        super(mensagem);
    }
}