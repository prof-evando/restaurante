package br.com.fiap.restaurante.model;

import java.util.List;

import br.com.fiap.restaurante.exceptions.DeliveryException;

public class GerenciadorDelivery {
    /**
     * Campos da classe GerenciadorDelivery
     */
    private List<Motoboy> listaMotoboy;
    private List<Pedido> listaPedido;
    private List<Cliente> listaCliente;
   

	/**
     * GET E SET
     * @return
     */
    public List<Motoboy> getListaMotoboy() {
        return listaMotoboy;
    }
    public void setListaMotoboy(List<Motoboy> listaMotoboy) {
        this.listaMotoboy = listaMotoboy;
    }
    public List<Pedido> getListaPedido() {
        return listaPedido;
    }
    public void setListaPedido(List<Pedido> listaPedido) {
        this.listaPedido = listaPedido;
    }
    public List<Cliente> getListaCliente() {
        return listaCliente;
    }
    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }
    
 
    /**
     * Método que aloca os Motoboys para os Clientes e aplica uma regra de exceção, caso não tenha motoboys disponíveis
     * @param listaMotoboy
     * @param cliente
     * @return
     * @throws DeliveryException
     */
    public Motoboy alocarMotoboy(List<Motoboy> listaMotoboy, Cliente cliente) throws DeliveryException {
        System.out.println("\n--- ALOCAÇÃO ---\n");
        Motoboy funcionario = null;
        boolean encontrouMotoboy = false;
        
        for (Motoboy motoboy : listaMotoboy) {
            if (!motoboy.getEhOcupado()) {
                System.out.println("Motoboy " + motoboy.getNome() + " foi alocado para o cliente " + cliente.getNome());
                motoboy.setEhOcupado(true);
                encontrouMotoboy = true;
                funcionario = motoboy;
                break;
            }
        }
        
        if (!encontrouMotoboy) {
            throw new DeliveryException("Nenhum motoboy disponível para o cliente " + cliente.getNome() + " no momento.");
        }
        
        return funcionario;
    }

    /**
     * Cria o pedido, passando uma lista de cada classe
     * @param listaCliente
     * @param estabelecimento
     * @param listaMotoboy
     * @throws DeliveryException
     */
    public void criarPedidos(List<Cliente> listaCliente, Estabelecimento estabelecimento, List<Motoboy> listaMotoboy) throws DeliveryException {
        int pinNumber = 1;

        for (Cliente cliente : listaCliente) {
          /**  Pedido pedido = new Pedido("pedido " + pinNumber, String.valueOf(pinNumber));
            Motoboy motoboy = alocarMotoboy(listaMotoboy, cliente);  // Aloca motoboy
            if (motoboy != null) {
                pedido.setMotoboy(motoboy);
                pedido.setCliente(cliente);
                pedido.setEstabelecimento(estabelecimento);

                // Insere o pedido no banco de dados
               //  PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
               // pedidoDAO.criarTabelaPedido();
                // pedidoDAO.criarTriggerPedido();
                // pedidoDAO.incluirPedido(pedido);
                

                System.out.println(pedido.toString());  // Exibe a confirmação do pedido
                pinNumber++;
            }*/
        }
    }

		
    
     
}
	

