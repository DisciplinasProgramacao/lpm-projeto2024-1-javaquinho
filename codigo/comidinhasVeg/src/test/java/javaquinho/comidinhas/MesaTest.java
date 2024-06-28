package javaquinho.comidinhas;

import org.junit.jupiter.api.Test;

import javaquinho.comidinhas.models.Mesa;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;


public class MesaTest {
    
    private Mesa mesa;

@BeforeEach

    /**
     * Configuração inicial antes de cada teste.
     * Cria uma instância de Mesa com capacidade para 4 pessoas.
     */
    public void setUp() {
        mesa = new Mesa(1,4, false);
    }


    /**
     * Testa a criação de uma mesa com a capacidade correta.
     * Verifica se a mesa está inicialmente desocupada.
     */
    @Test
    public void testeCriarMesa() {
        assertEquals(4, mesa.getCapacidade());
        assertFalse(mesa.isOcupada());
    }


    /**
     * Testa o método ocupar.
     * Verifica se a mesa é marcada como ocupada corretamente.
     */
    @Test
    public void testeOcupar() {
        mesa.ocupar();
        assertTrue(mesa.isOcupada());
    }


    /**
     * Testa o método desocupar.
     * Verifica se a mesa é marcada como desocupada corretamente.
     */
    @Test
    public void testeDesocupar() {
        mesa.ocupar();
        mesa.desocupar();
        assertFalse(mesa.isOcupada());
    }


    /**
     * Testa o método estahLiberada.
     * Verifica se a mesa está liberada para uma quantidade específica de pessoas.
     * Também verifica o comportamento quando a mesa está ocupada.
     */
    @Test
    public void testeEstahLiberada() {
        assertTrue(mesa.estahLiberada(4));
        assertFalse(mesa.estahLiberada(5));
        mesa.ocupar();
        assertFalse(mesa.estahLiberada(2));
    }


    /**
     * Testa o construtor que recebe a capacidade como parâmetro.
     * Verifica se a mesa criada com este construtor tem os valores corretos.
     */
    @Test
    public void testeConstrutorComCapacidade() {
        Mesa mesaComCapacidade = new Mesa(6);
        assertEquals(6, mesaComCapacidade.getCapacidade());
        assertFalse(mesaComCapacidade.isOcupada());
    }

    /**
     * Testa se a mesa não permite alocar mais pessoas que sua capacidade.
     * Verifica se o método estahLiberada retorna false quando o número de pessoas excede a capacidade.
     */
    @Test
    public void testeAlocarMaisQueCapacidade() {
        assertFalse(mesa.estahLiberada(5));
        assertTrue(mesa.estahLiberada(4));
    }
}
