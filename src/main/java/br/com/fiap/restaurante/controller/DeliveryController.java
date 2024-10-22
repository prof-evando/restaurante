package br.com.fiap.restaurante.controller;

import br.com.fiap.restaurante.model.ClienteDAO;
import br.com.fiap.restaurante.model.ClienteDAOImpl;
import br.com.fiap.restaurante.model.Endereco;
import br.com.fiap.restaurante.model.EnderecoDAO;
import br.com.fiap.restaurante.model.EnderecoDAOImpl;
import br.com.fiap.restaurante.model.Estabelecimento;
import br.com.fiap.restaurante.model.EstabelecimentoDAO;
import br.com.fiap.restaurante.model.EstabelecimentoDAOImpl;
import br.com.fiap.restaurante.model.Motoboy;
import br.com.fiap.restaurante.model.MotoboyDAO;
import br.com.fiap.restaurante.model.MotoboyDAOImpl;


public class DeliveryController {

	private EstabelecimentoDAO estabelecimentoDAO;
	private EnderecoDAO enderecoDAO;
	private ClienteDAO clienteDAO;
	private MotoboyDAO motoboyDAO;
	
	public DeliveryController() {
		estabelecimentoDAO = new EstabelecimentoDAOImpl();
		enderecoDAO = new EnderecoDAOImpl();
		clienteDAO = new ClienteDAOImpl();
		motoboyDAO = new MotoboyDAOImpl();
		
	}
	
	public DeliveryController(boolean isSushi) {
		// TODO estabelecimentoDAO = EstabelecimentoDAOFactory.criarEstabelecimentoDAO(isSushi);
	}
	
	public void incluirEstabelecimento(Endereco enderecoID, Estabelecimento estabelecimento) {
		System.out.println("Tel: " + estabelecimento.getTel());
		estabelecimentoDAO.incluirEstabelecimento(enderecoID, estabelecimento);
	}
	
	public void criarTabelaEstabelecimento() {
		estabelecimentoDAO.criarTabelaEstabelecimento();
	}
	
	public void incluirEndereco(Endereco endereco) {
		enderecoDAO.incluirEndereco(endereco);
	}
	
	public void criarTabelaEndereco() {
		enderecoDAO.criarTabelaEndereco();
	}
	
	public void criarSequenceEndereco() {
		enderecoDAO.criarSequenceEndereco();
	}
	public void criarTriggerEndereco() {
		enderecoDAO.criarTriggerEndereco();
	}
	
	public void incluirCliente(Endereco enderecoID, String nome, String email) {
		clienteDAO.incluirCliente(enderecoID, nome, email);
	}
	
	public void criarTabelaCliente() {
		clienteDAO.criarTabelaCliente();
	}
	
	public void incluirMotoboy(Motoboy motoboy) {
		motoboyDAO.incluirMotoboy(motoboy);
	}
	
	public void criarTabelaMotoboy() {
		motoboyDAO.criarTabelaMotoboy();
	}
	
	public void criarSequenceMotoboy() {
		motoboyDAO.criarSequenceMotoboy();
	}
	
	public void criarTriggerMotoboy() {
		motoboyDAO.criarTriggerMotoboy();
	}
	
}
