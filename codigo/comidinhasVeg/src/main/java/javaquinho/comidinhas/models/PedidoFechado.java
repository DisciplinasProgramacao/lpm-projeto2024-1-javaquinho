package javaquinho.comidinhas.models;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;

@Entity
public class PedidoFechado extends Pedido<MenuFechado> {
    private static int MAXIMO_ITENS_PESSOA = 2;

    /**
     * Construtor que inicializa a quantidade de pessoas.
     *
     * @param qntPessoas quantidade de pessoas
     */
    public PedidoFechado(int qntPessoas) {
        super(qntPessoas);
    }

    /**
     * Construtor que inicializa a quantidade de pessoas e o menu.
     *
     * @param qntPessoas quantidade de pessoas
     * @param menu       menu associado ao pedido
     * @throws MenuInvalidoException se o menu for inválido
     */
    public PedidoFechado(int qntPessoas, MenuFechado menu) throws MenuInvalidoException {
        super(qntPessoas, menu);
    }

    /**
     * Construtor padrão.
     */
    public PedidoFechado() {
        super();
    }

    /**
     * Define o menu associado ao pedido.
     *
     * @param menu menu a ser associado
     * @throws MenuInvalidoException se o menu for inválido
     */
    @Override
    public void setMenu(MenuFechado menu) throws MenuInvalidoException {
        try {
            this.menu = menu;
        } catch (Exception e) {
            throw new MenuInvalidoException(MenuAberto.class.getName());
        }
    }

    /**
     * Retorna a quantidade máxima de produtos que o pedido pode ter
     *
     * @return quantidade máxima de produtos
     */
    public int getMaximoItens() {
        return this.qntPessoas * MAXIMO_ITENS_PESSOA;
    }

    /**
     * Retorna o valor total do pedido somando os preços dos produtos.
     *
     * @return valor total do pedido
     */
    @Override
    public double getSomarTotal() {
        return this.qntPessoas * 32;
    }

    /**
     * Adiciona um produto ao pedido.
     *
     * @param produto produto a ser adicionado
     * @throws NaoExisteMenuException          se não houver menu associado ao
     *                                         pedido
     * @throws ProdutoNaoExisteNoMenuException se o produto não existir no menu
     */
    @Override
    public void addProduto(Produto produto)
            throws NaoExisteMenuException, ProdutoNaoExisteNoMenuException, LimiteProdutosException {
        if (this.getMenu() == null) {
            throw new NaoExisteMenuException();
        } else if (this.getProdutos().size() >= MAXIMO_ITENS_PESSOA) {
            throw new LimiteProdutosException(MAXIMO_ITENS_PESSOA);
        } else if (!this.getMenu().produtoExiste(produto)) {
            throw new ProdutoNaoExisteNoMenuException();
        } else {
            this.produtos.add(produto);
        }
    }
}