package javaquinho.comidinhas;

import com.fasterxml.jackson.databind.ObjectMapper;
import javaquinho.comidinhas.controllers.RestauranteController;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.models.*;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.repositories.MesaRepository;
import javaquinho.comidinhas.repositories.ProdutoRepository;
import javaquinho.comidinhas.repositories.RequisicaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestauranteController restauranteController;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private MesaRepository mesaRepository;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private Mesa mesa1, mesa2, mesa3;
    private Produto produto1, produto2, produto3;
    private Menu menuAberto, menuFechado;
    private Requisicao requisicao;

    @BeforeEach
    public void setup() {
        cliente = new Cliente(null, "Leandra", "21880084323", "92312315401");

        mesa1 = new Mesa(4);
        mesa2 = new Mesa(6);
        mesa3 = new Mesa(8);

        produto1 = new Produto();
        produto1.setNome("Água");
        produto1.setPreco(3.0);
        produto2 = new Produto();
        produto2.setNome("Strogonoff de Cogumelos");
        produto2.setPreco(35.0);
        produto3 = new Produto();
        produto3.setNome("Escondidinho de Inhame");
        produto3.setPreco(18.0);

        menuAberto = new MenuAberto(new HashSet<>(Arrays.asList(produto1, produto2, produto3)));
        
        try {
            menuFechado = new MenuFechado(new HashSet<>(Arrays.asList(produto1, produto2)));
        } catch (LimiteProdutosException e) {
            e.printStackTrace();
        }

        requisicao = new Requisicao(cliente, 4);
    }

    @Test
    public void testCriarClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
    
        // Configura o mock para retornar os clientes ao chamar saveAll
        doReturn(clientes).when(clienteRepository).saveAll(clientes);
    
        // Simula a requisição HTTP POST para /restaurante/clientes
        mockMvc.perform(post("/restaurante/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientes)))
                .andExpect(status().isOk())
                .andExpect(content().string("Clientes criados com sucesso."));
    }

    @Test
    public void testCriarMesas() throws Exception {
        List<Mesa> mesas = Arrays.asList(mesa1, mesa2, mesa3);

        mockMvc.perform(post("/restaurante/mesas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mesas)))
                .andExpect(status().isOk())
                .andExpect(content().string("Mesas criadas com sucesso."));
    }

    @Test
    public void testCriarProdutos() throws Exception {
        List<Produto> produtos = Arrays.asList(produto1, produto2, produto3);

        mockMvc.perform(post("/restaurante/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtos)))
                .andExpect(status().isOk())
                .andExpect(content().string("Os produtos do restaurante foram iniciados"));
    }

    @Test
    public void testCriarMenuAberto() throws Exception {
        Set<Produto> produtos = new HashSet<>(Arrays.asList(produto1, produto2, produto3));

        mockMvc.perform(post("/restaurante/menu/aberto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtos)))
                .andExpect(status().isOk())
                .andExpect(content().string("Menu aberto criado com sucesso!"));
    }

    @Test
    public void testCriarMenuFechado() throws Exception {
        Set<Produto> produtos = new HashSet<>(Arrays.asList(produto1, produto2));

        mockMvc.perform(post("/restaurante/menu/fechado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtos)))
                .andExpect(status().isOk())
                .andExpect(content().string("Menu fechado criado com sucesso!"));
    }

    @Test
    public void testCriarRequisicao() throws Exception {
        mockMvc.perform(post("/restaurante/{idCliente}/{quantPessoas}", 1, 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Mesa alocada para a requisição com sucesso!"));
    }

    @Test
    public void testAtribuirMenuParaRequisicao() throws Exception {
        mockMvc.perform(post("/restaurante/atribuir-menu/{idRequisicao}/{idMenu}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Menu atribuído à requisição com sucesso!"));
    }

    @Test
    public void testAdicionarProdutoAoPedido() throws Exception {
        mockMvc.perform(post("/restaurante/adicionar-produto/{idRequisicao}/{idProduto}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto adicionado ao pedido com sucesso."));
    }

    @Test
    public void testDesalocarMesaDeRequisicao() throws Exception {
        mockMvc.perform(put("/restaurante/desalocar/{requisicaoId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Mesa desalocada. Requisição finalizada com sucesso!\nTotal do Pedido: "));
    }
}
