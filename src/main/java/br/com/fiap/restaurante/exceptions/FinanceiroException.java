package br.com.fiap.restaurante.exceptions;

public class FinanceiroException extends Exception {

    public FinanceiroException(String message) {
        super(message);
    }

    public FinanceiroException(String message, Throwable cause) {
        super(message, cause);
    }
}
