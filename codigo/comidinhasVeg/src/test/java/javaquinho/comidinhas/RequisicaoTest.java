package javaquinho.comidinhas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.models.Requisicao;

public class RequisicaoTest {

    private Cliente cliente;
    private Requisicao requisicao;
    private Mesa mesa;
    private Produto produto;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        
        mesa = new Mesa();
        mesa.setIdMesa(1);
        mesa.setCapacidade(4);
        
        produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(10.0);
        
        requisicao = new Requisicao(cliente, 3);
    }

    @Test
    public void testRequisicaoConstrutor() {
        assertNotNull(requisicao);
        assertEquals(cliente, requisicao.getCliente());
        assertEquals(3, requisicao.getQuantPessoas());
        assertNull(requisicao.getMesa());
        assertFalse(requisicao.isEncerrada());
    }

    @Test
    public void testAlocarMesa() {
        requisicao.alocarMesa(mesa);
        assertNotNull(requisicao.getMesa());
        assertEquals(mesa, requisicao.getMesa());
        assertNotNull(requisicao.getEntrada());
    }

    @Test
    public void testEncerrar() {
        requisicao.alocarMesa(mesa);
        Mesa mesaEncerrada = requisicao.encerrar();
        assertTrue(requisicao.isEncerrada());
        assertNotNull(requisicao.getSaida());
        assertEquals(mesa, mesaEncerrada);
    }

    @Test
    public void testAdicionarProduto() {
        requisicao.adicionarProduto(produto);
        assertNotNull(requisicao.getPedido());
        assertTrue(requisicao.getPedido().getProdutos().contains(produto));
    }

    @Test
    public void testRemoverProduto() {
        requisicao.adicionarProduto(produto);
        requisicao.removerProduto(produto);
        assertFalse(requisicao.getPedido().getProdutos().contains(produto));
    }

    @Test
    public void testExibirConta() {
        requisicao.adicionarProduto(produto);
        assertEquals(10.0, requisicao.exibirConta());
    }

    @Test
    public void testExibirValorPorPessoa() {
        requisicao.adicionarProduto(produto);
        assertEquals(10.0 / 3, requisicao.exibirValorPorPessoa());
    }

    @Test
    public void testToString() {
        requisicao.alocarMesa(mesa);
        requisicao.adicionarProduto(produto);
        String output = requisicao.toString();
        assertTrue(output.contains("Cliente Teste"));
        assertTrue(output.contains("Produto Teste - R$10.0"));
        assertTrue(output.contains("Total: 10.0"));
        assertTrue(output.contains("Valor por pessoa: " + 10.0 / 3));
    }
}
