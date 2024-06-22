package javaquinho.comidinhas.excecoes;

public class MenuInvalidoException extends Exception {
    public MenuInvalidoException(String menu){
        super("O pedido só pode ter menus do tipo " + menu);
    }
}
