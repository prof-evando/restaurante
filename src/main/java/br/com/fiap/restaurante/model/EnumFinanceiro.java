package br.com.fiap.restaurante.model;

public class EnumFinanceiro {

    public enum TipoPedido {
        PRESENCIAL("P", "Presencial"),
        DELIVERY("D", "Delivery");

        private final String codigo;
        private final String descricao;

        TipoPedido(String codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public String getCodigo() {
            return codigo;
        }
         public String getDescricao() {
            return descricao;
        }

        public static TipoPedido fromCodigo(String codigo) {
            for (TipoPedido tipo : TipoPedido.values()) {
                if (tipo.getCodigo().equals(codigo)) {
                    return tipo;
                }
            }
            throw new IllegalArgumentException("Código de tipo de pedido inválido: " + codigo);
        }
    }

    public enum MetodoPagamento {
        CARTAO_CREDITO("CC", "Cartão de Crédito"),
        CARTAO_DEBITO("CD", "Cartão de Débito"),
        DINHEIRO("DN", "Dinheiro"),
        TRANSFERENCIA_BANCARIA("TR", "Transferência Bancária");

        private final String codigo;
        private final String descricao;

        MetodoPagamento(String codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public static MetodoPagamento fromCodigo(String codigo) {
            for (MetodoPagamento metodo : MetodoPagamento.values()) {
                if (metodo.getCodigo().equals(codigo)) {
                    return metodo;
                }
            }
            throw new IllegalArgumentException("Código de método de pagamento inválido: " + codigo);
        }
    }

    public enum StatusPagamento {
        CONCLUIDO("C", "Concluído"),
        PENDENTE("P", "Pendente"),
        CANCELADO("X", "Cancelado");

        private final String codigo;
        private final String descricao;

        StatusPagamento(String codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public static StatusPagamento fromCodigo(String codigo) {
            for (StatusPagamento status : StatusPagamento.values()) {
                if (status.getCodigo().equals(codigo)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Código de status inválido: " + codigo);
        }
    }
}

