package javaquinho.comidinhas.excecoes;

public class NaoExisteMenuException extends Exception{
    public NaoExisteMenuException(){
        super("Não existe nenhum menu no pedido!");
    }
}
