package javaquinho.comidinhas.models;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;

/**
 * Classe representando um PedidoAberto, que é um tipo específico de Pedido.
 */
@Entity
public class PedidoAberto extends Pedido<MenuAberto> {

    /**
     * Construtor que inicializa a quantidade de pessoas.
     *
     * @param qntPessoas quantidade de pessoas
     */
    public PedidoAberto(int qntPessoas){
        super(qntPessoas);
    }

    /**
     * Construtor que inicializa a quantidade de pessoas e o menu.
     *
     * @param qntPessoas quantidade de pessoas
     * @param menu menu associado ao pedido
     * @throws MenuInvalidoException se o menu for inválido
     */
    public PedidoAberto(int qntPessoas, MenuAberto menu) throws MenuInvalidoException{
        super(qntPessoas, menu);
    }

    /**
     * Construtor padrão.
     */
    public PedidoAberto(){
        super();
    }
    
    /**
     * Define o menu associado ao pedido.
     *
     * @param menu menu a ser associado
     * @throws MenuInvalidoException se o menu for inválido
     */
    @Override
    public void setMenu(MenuAberto menu) throws MenuInvalidoException{
        try {
            this.menu = menu;
        } catch(Exception e){
            throw new MenuInvalidoException(MenuAberto.class.getName());
        }
    }

    /**
     * Retorna o valor total do pedido somando os preços dos produtos.
     *
     * @return valor total do pedido
     */
    @Override
    public double getSomarTotal() {
        return getProdutos().stream()
               .mapToDouble(Produto::getPreco)
               .sum();
    }

    /**
     * Adiciona um produto ao pedido.
     *
     * @param produto produto a ser adicionado
     * @throws NaoExisteMenuException se não houver menu associado ao pedido
     * @throws ProdutoNaoExisteNoMenuException se o produto não existir no menu
     */
    @Override
    public void addProduto(Produto produto) throws NaoExisteMenuException, ProdutoNaoExisteNoMenuException{
        if (this.getMenu() == null){
            throw new NaoExisteMenuException();
        }
        else if (!this.getMenu().produtoExiste(produto)){
            throw new ProdutoNaoExisteNoMenuException();
        }
        else {
            this.produtos.add(produto);
        }
    }
}