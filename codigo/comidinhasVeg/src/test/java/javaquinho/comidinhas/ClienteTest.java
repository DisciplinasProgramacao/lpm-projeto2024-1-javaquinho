package javaquinho.comidinhas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javaquinho.comidinhas.models.Cliente;

/**
 * Classe de teste para a classe Cliente.
 */
public class ClienteTest {

    private Cliente cliente;

    /**
     * Configuração inicial antes de cada teste.
     * Cria uma nova instância de Cliente e define valores padrão.
     */
    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("João da Silva");
        cliente.setTelefone("12345678901");
        cliente.setCpf("11122233344");
    }

    /**
     * Testa o método getId().
     * Verifica se o ID retornado é igual ao valor esperado.
     */
    @Test
    public void testGetId() {
        assertEquals(1, cliente.getId());
    }

    /**
     * Testa o método setId().
     * Verifica se o ID é atualizado corretamente.
     */
    @Test
    public void testSetId() {
        cliente.setId(2);
        assertEquals(2, cliente.getId());
    }

    /**
     * Testa o método getNome().
     * Verifica se o nome retornado é igual ao valor esperado.
     */
    @Test
    public void testGetNome() {
        assertEquals("João da Silva", cliente.getNome());
    }

    /**
     * Testa o método setNome().
     * Verifica se o nome é atualizado corretamente.
     */
    @Test
    public void testSetNome() {
        cliente.setNome("Maria Oliveira");
        assertEquals("Maria Oliveira", cliente.getNome());
    }

    /**
     * Testa o método getTelefone().
     * Verifica se o telefone retornado é igual ao valor esperado.
     */
    @Test
    public void testGetTelefone() {
        assertEquals("12345678901", cliente.getTelefone());
    }

    /**
     * Testa o método setTelefone().
     * Verifica se o telefone é atualizado corretamente.
     */
    @Test
    public void testSetTelefone() {
        cliente.setTelefone("10987654321");
        assertEquals("10987654321", cliente.getTelefone());
    }

    /**
     * Testa o método getCpf().
     * Verifica se o CPF retornado é igual ao valor esperado.
     */
    @Test
    public void testGetCpf() {
        assertEquals("11122233344", cliente.getCpf());
    }

    /**
     * Testa o método setCpf().
     * Verifica se o CPF é atualizado corretamente.
     */
    @Test
    public void testSetCpf() {
        cliente.setCpf("99988877766");
        assertEquals("99988877766", cliente.getCpf());
    }

    /**
     * Testa os métodos equals() e hashCode().
     * Verifica se dois objetos Cliente são iguais quando têm os mesmos valores e diferentes quando têm valores distintos.
     */
    @Test
    public void testEqualsAndHashCode() {
        Cliente outroCliente = new Cliente(1, "João da Silva", "12345678901", "11122233344");
        assertEquals(cliente, outroCliente);
        assertEquals(cliente.hashCode(), outroCliente.hashCode());

        outroCliente.setId(2);
        assertNotEquals(cliente, outroCliente);
        assertNotEquals(cliente.hashCode(), outroCliente.hashCode());
    }
}
