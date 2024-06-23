 package javaquinho.comidinhas;


 import static org.junit.jupiter.api.Assertions.assertEquals;

 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;

 import javaquinho.comidinhas.models.Cliente;



 public class ClienteTest {

     private Cliente c;

     @BeforeEach
     public void setup(){
         c = new Cliente(null, "João", "31988776655", "23726737356");
     }

     @Test
     public void testGetNome() {
         assertEquals("João", c.getNome());
     }

     @Test
     public void testGetTelContato() {
         assertEquals("31988776655", c.getTelefone());
     }

     @Test
     public void testGetCPF() {
         assertEquals("23726737356", c.getCpf());
     }

     @Test
     public void coletarDadosCliente() {
         assertEquals("ID: 1, Nome: João, Telefone: 31988776655, CPF: 23726737356", c.toString());
     }
 }
