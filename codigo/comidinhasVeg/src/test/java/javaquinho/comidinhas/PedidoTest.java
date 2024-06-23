// package javaquinho.comidinhas;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import javaquinho.comidinhas.excecoes.LimiteProdutosException;
// import javaquinho.comidinhas.models.Pedido;
// import javaquinho.comidinhas.models.Produto;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// public class PedidoTest {

//     private Pedido pedido;
//     private Produto produto1;
//     private Produto produto2;

//     @BeforeEach
//     public void setUp() {
//         produto1 = new Produto();
//         produto1.setPreco(10.0);
//         produto1.setNome("Produto 1");

//         produto2 = new Produto();
//         produto2.setPreco(20.0);
//         produto2.setNome("Produto 2");

//         pedido = new Pedido();

//         try {
//             pedido.addProduto(produto1);
//             pedido.addProduto(produto2);
//         }
//         catch (LimiteProdutosException e){
//             System.out.println(e);
//         }
        
//     }

//     @Test
//     public void testGetProdutos() {
//         List<Produto> produtos = pedido.getProdutos();

//         assertEquals(2, produtos.size());
//         assertEquals(10.0, produtos.get(0).getPreco(), 0.001);
//         assertEquals(20.0, produtos.get(1).getPreco(), 0.001);
//     }

//     @Test
//     public void testAddProduto() {
//         Produto produto3 = new Produto();
//         produto3.setPreco(15.0);
//         produto3.setNome("Produto 3");

//         try {
//             pedido.addProduto(produto3);
//         }
//         catch (LimiteProdutosException e){
//             System.out.println(e);
//         }

//         assertEquals(3, pedido.getProdutos().size());
//         assertEquals(15.0, pedido.getProdutos().get(2).getPreco(), 0.001);
//     }

//     @Test
//     public void testRemoveProduto() {
//         pedido.removeProduto(produto1);

//         assertEquals(1, pedido.getProdutos().size());
//         assertEquals(20.0, pedido.getProdutos().get(0).getPreco(), 0.001);
//     }

//     @Test
//     public void testGetSomarTotal() {
//         double total = pedido.getSomarTotal();

//         assertEquals(30.0, total, 0.001);
//     }
// }
