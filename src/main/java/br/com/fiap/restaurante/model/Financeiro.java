package br.com.fiap.restaurante.model;

public class Financeiro {

    public Integer getIdTrasacao() {
		return idTrasacao;
	}

	public void setIdTrasacao(Integer idTrasacao) {
		this.idTrasacao = idTrasacao;
	}

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public String getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(String statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public String getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	private Integer idTrasacao;
    private Integer idPedido;
    private Integer idCliente;
    private String dataHora;
    private double valorTotal;
    private String metodoPagamento;
    private String statusPagamento;
    private String tipoPedido;
    private String observacoes;

    public Financeiro(Integer idTrasacao, Integer idPedido, Integer idCliente, String dataHora, double valorTotal, String metodoPagamento, String statusPagamento, String tipoPedido, String observacoes) {
        this.idTrasacao = idTrasacao;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.statusPagamento = statusPagamento;
        this.tipoPedido = tipoPedido;
        this.observacoes = observacoes;
    }

}
