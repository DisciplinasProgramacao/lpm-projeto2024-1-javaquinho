package javaquinho.comidinhas;

import javaquinho.comidinhas.controllers.RestauranteController;
import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.models.Menu;
import javaquinho.comidinhas.models.Restaurante;
import javaquinho.comidinhas.models.Requisicao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestauranteControllerTest {

    @Mock
    private Restaurante restaurante;

    @InjectMocks
    private RestauranteController restauranteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarCliente() {
        Cliente cliente = new Cliente();
        when(restaurante.criarCliente(cliente)).thenReturn(cliente);

        ResponseEntity<Cliente> response = restauranteController.criarCliente(cliente);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());

        verify(restaurante, times(1)).criarCliente(cliente);
    }

    @Test
    public void testCriarMesa() {
        Mesa mesa = new Mesa();
        when(restaurante.criarMesa(mesa)).thenReturn(mesa);

        ResponseEntity<Mesa> response = restauranteController.criarMesa(mesa);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mesa, response.getBody());

        verify(restaurante, times(1)).criarMesa(mesa);
    }

    @Test
    public void testCriarMenu() {
        Menu menu = new Menu();
        when(restaurante.criarMenu(menu)).thenReturn(menu);

        ResponseEntity<Menu> response = restauranteController.criarMenu(menu);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(menu, response.getBody());

        verify(restaurante, times(1)).criarMenu(menu);
    }

    @Test
    public void testAlocarMesaParaRequisicao() {
        Long requisicaoId = 1L;
        when(restaurante.alocarMesaParaRequisicao(requisicaoId)).thenReturn("Mesa alocada com sucesso.");

        ResponseEntity<String> response = restauranteController.alocarMesaParaRequisicao(requisicaoId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mesa alocada com sucesso.", response.getBody());

        verify(restaurante, times(1)).alocarMesaParaRequisicao(requisicaoId);
    }

    @Test
    public void testDesalocarMesaDeRequisicao() {
        Long requisicaoId = 1L;
        when(restaurante.desalocarMesaDeRequisicao(requisicaoId)).thenReturn("Mesa desalocada com sucesso.");

        ResponseEntity<String> response = restauranteController.desalocarMesaDeRequisicao(requisicaoId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mesa desalocada com sucesso.", response.getBody());

        verify(restaurante, times(1)).desalocarMesaDeRequisicao(requisicaoId);
    }

    @Test
    public void testDesalocarMesaDeRequisicaoAtualizaSaida() {
        Long requisicaoId = 1L;
        Requisicao requisicao = new Requisicao();
        Mesa mesa = new Mesa();
        requisicao.setMesa(mesa);
        
        when(restaurante.desalocarMesaDeRequisicao(requisicaoId)).thenAnswer(invocation -> {
            requisicao.setSaida(LocalDateTime.now());
            requisicao.getMesa().desocupar();
            requisicao.setMesa(null);
            return "Mesa desalocada com sucesso.";
        });

        ResponseEntity<String> response = restauranteController.desalocarMesaDeRequisicao(requisicaoId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mesa desalocada com sucesso.", response.getBody());
        assertEquals(null, requisicao.getMesa());
        assertEquals(false, mesa.isOcupada());
        assertEquals(LocalDateTime.now().getDayOfYear(), requisicao.getSaida().getDayOfYear());

        verify(restaurante, times(1)).desalocarMesaDeRequisicao(requisicaoId);
    }
}
