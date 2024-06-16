package javaquinho.comidinhas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Requisicao;
import javaquinho.comidinhas.models.Restaurante;

public class RestauranteTest {
    Cliente cliente;
    Restaurante res;
    Requisicao req;

    @BeforeAll
    public void setUp(){
        res = new Restaurante("Test");
        cliente = new Cliente(null, "Helio", "01234567890", "01234567890", res);
    }

    @Test
    public void testLocalizarCliente(){
        assertEquals(cliente, res.localizarCliente("01234567890"));
    }

    @Test
    public void testCriarRequisicao(){
        res.criarRequisicao("01234567890",8);
        assertEquals(new Requisicao(cliente, 8, res), req);
    }

    @Test
    public void testAlocarRequisicao(){
        res.criarRequisicao("01234567890",8);
        req = res.localizarRequisicaoEmAtendimento("01234567890");
        assertTrue(req.getAtendida());
    }

}