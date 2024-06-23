package javaquinho.comidinhas;

import org.junit.jupiter.api.Test;

import javaquinho.comidinhas.models.Mesa;
import static org.junit.jupiter.api.Assertions.*;


public class MesaTest {
    

 @Test
 public void testSetCapacidade() {
     Mesa mesa = new Mesa();
     mesa.setCapacidade(4);
     assertEquals(4, mesa.getCapacidade());
 }


 @Test
 public void testSetCapacidadeInvalida() {
     Mesa mesa = new Mesa();
     mesa.setCapacidade(5); 
     assertEquals(0, mesa.getCapacidade());
 }

 @Test
 public void testOcuparEDesocupar() {
     Mesa mesa = new Mesa();
     assertFalse(mesa.estahLiberada(0));
     mesa.ocupar();
     assertTrue(mesa.estahLiberada(0));
     mesa.desocupar();
     assertFalse(mesa.estahLiberada(0));
 }

}
