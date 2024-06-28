package javaquinho.comidinhas.excecoes;

public class ProdutoNaoExisteNoMenuException extends Exception{
    public ProdutoNaoExisteNoMenuException(){
        super("O produto não existe no menu que o pedido escolheu!");
    }
}
