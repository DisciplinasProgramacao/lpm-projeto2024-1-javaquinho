package javaquinho.comidinhas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.HashSet;
import java.util.Set;

import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
import javaquinho.comidinhas.models.MenuAberto;
import javaquinho.comidinhas.models.PedidoAberto;
import javaquinho.comidinhas.models.Produto;

/**
 * Classe de teste para a classe PedidoAberto.
 */
public class PedidoAbertoTest {

    private MenuAberto menuAberto;
    private PedidoAberto pedidoAberto;

    /**
     * Configuração inicial para cada teste. Cria um menu aberto com produtos e um pedido aberto.
     * @throws MenuInvalidoException se o menu criado for inválido.
     */
    @BeforeEach
    public void setUp() throws MenuInvalidoException {
        Produto produto1 = new Produto();
        produto1.setNome("Água");
        produto1.setPreco(20.0);

        Produto produto2 = new Produto();
        produto2.setNome("Strogonoff de Cogumelos");
        produto2.setPreco(5.0);

        Set<Produto> produtos = new HashSet<>();
        produtos.add(produto1);
        produtos.add(produto2);

        menuAberto = new MenuAberto(produtos);
        pedidoAberto = new PedidoAberto(2, menuAberto);
    }

    /**
     * Testa a adição de produtos ao pedido.
     * @throws NaoExisteMenuException se o menu não existir.
     * @throws ProdutoNaoExisteNoMenuException se o produto não existir no menu.
     */
    @Test
    public void testAdicionarProdutos() throws NaoExisteMenuException, ProdutoNaoExisteNoMenuException {
        Produto produto1 = menuAberto.getProdutos().iterator().next();

        pedidoAberto.addProduto(produto1);

        Assertions.assertTrue(pedidoAberto.getProdutos().contains(produto1), "Produto deveria estar no pedido");
    }

    /**
     * Testa o cálculo do total do pedido.
     * @throws NaoExisteMenuException se o menu não existir.
     * @throws ProdutoNaoExisteNoMenuException se o produto não existir no menu.
     */
    @Test
    public void testCalculoTotal() throws NaoExisteMenuException, ProdutoNaoExisteNoMenuException {
        Produto produto1 = menuAberto.getProdutos().iterator().next();
        Produto produto2 = menuAberto.getProdutos().iterator().next();

        pedidoAberto.addProduto(produto1);
        pedidoAberto.addProduto(produto2);

        double totalEsperado = produto1.getPreco() + produto2.getPreco();

        Assertions.assertEquals(totalEsperado, pedidoAberto.getSomarTotal(), 0.01, "Total do pedido deveria ser igual à soma dos preços dos produtos");
    }

    /**
     * Testa a tentativa de adicionar um produto não existente no menu.
     */
    @Test
    public void testAdicionarProdutoInvalido() {
        Produto produtoInvalido = new Produto();
        produtoInvalido.setNome("Hamburguer");
        produtoInvalido.setPreco(15.0);

        Assertions.assertThrows(ProdutoNaoExisteNoMenuException.class, () -> {
            pedidoAberto.addProduto(produtoInvalido);
        }, "Lançar ProdutoNaoExisteNoMenuException ao tentar adicionar um produto não existente no menu");
    }
}