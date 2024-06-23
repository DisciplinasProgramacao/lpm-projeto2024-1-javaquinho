package javaquinho.comidinhas.models;

import java.util.Set;

import jakarta.persistence.Entity;
/**
 * A classe `MenuAberto` representa um tipo específico de menu que permite
 * adicionar e definir produtos sem limite.
 */
@Entity
public class MenuAberto extends Menu {

    /**
     * Construtor padrão
     */
    public MenuAberto() {
    }

    /**
     * Construtor que inicializa a classe com um conjunto de produtos.
     *
     * @param produtos o conjunto de produtos para inicializar o menu
     */
    public MenuAberto(Set<Produto> produtos) {
        this.setProdutos(produtos);
    }

    /**
     * Adiciona um produto ao menu.
     * 
     * Este método sobrescreve o método abstrato `adicionarProduto` da classe
     * `Menu`.
     *
     * @param produto o produto a ser adicionado ao menu
     */
    @Override
    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    /**
     * Define o conjunto de produtos para o menu.
     * 
     * Este método sobrescreve o método abstrato `setProdutos` da classe `Menu`.
     *
     * @param produtos o novo conjunto de produtos
     */
    @Override
    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }
}