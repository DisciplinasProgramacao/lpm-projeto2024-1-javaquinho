package javaquinho.comidinhas.excecoes;

public class LimiteProdutosException extends Exception{
    public LimiteProdutosException(int limite){
        super("O limite de " + limite + " produtos por pedido não pode ser ultrapassado!");
    }
}

