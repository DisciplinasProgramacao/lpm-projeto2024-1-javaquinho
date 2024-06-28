package javaquinho.comidinhas.models;

import java.util.Set;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;

/**
 * A classe `MenuFechado` representa um tipo específico de menu que permite
 * adicionar e definir produtos sem limite.
 */
@Entity
public class MenuFechado extends Menu {
    /**
     * Constante que define o número máximo de itens permitidos no menu.
     */
    private static int MAXIMO_ITENS = 5;

    /**
     * Construtor padrão
     */
    public MenuFechado() {
    }

    /**
     * Construtor que inicializa a classe com um conjunto de produtos.
     *
     * @param produtos o conjunto de produtos para inicializar o menu
     */
    public MenuFechado(Set<Produto> produtos) throws LimiteProdutosException {
        this.setProdutos(produtos);
    }

    /**
     * Adiciona um produto ao menu, respeitando o limite máximo de itens.
     * 
     * Este método sobrescreve o método abstrato `adicionarProduto` da classe
     * `Menu`.
     *
     * @param produto o produto a ser adicionado ao menu
     * @throws LimiteProdutosException se o número de produtos no menu já atingir o
     *                                 limite máximo permitido
     */
    @Override
    public void adicionarProduto(Produto produto) throws LimiteProdutosException {
        if (this.getProdutos().size() >= MAXIMO_ITENS) {
            throw new LimiteProdutosException(MAXIMO_ITENS);
        } else {
            this.produtos.add(produto);
        }
    }

    /**
     * Define o conjunto de produtos para o menu, respeitando o limite máximo de
     * itens.
     * 
     * Este método sobrescreve o método abstrato `setProdutos` da classe `Menu`.
     *
     * @param produtos o novo conjunto de produtos
     * @throws LimiteProdutosException se o número de produtos exceder o limite
     *                                 máximo permitido
     */
    @Override
    public void setProdutos(Set<Produto> produtos) throws LimiteProdutosException {
        if (produtos.size() > MAXIMO_ITENS) {
            throw new LimiteProdutosException(MAXIMO_ITENS);
        } else {
            this.produtos = produtos;
        }
    }

}