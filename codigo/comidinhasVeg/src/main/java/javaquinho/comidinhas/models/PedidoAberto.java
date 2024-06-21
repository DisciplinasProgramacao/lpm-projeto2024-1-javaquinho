package javaquinho.comidinhas.models;

import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;

public class PedidoAberto extends Pedido<MenuAberto> {
    @Override
    public double getSomarTotal() {
        return getProdutos().stream()
               .mapToDouble(Produto::getPreco)
               .sum();
    }

    @Override
    public void addProduto(Produto produto) throws NaoExisteMenuException, ProdutoNaoExisteNoMenuException{
        if (this.getMenu() == null){
            throw new NaoExisteMenuException();
        }
        else if (!this.getMenu().produtoExiste(produto)){
            throw new ProdutoNaoExisteNoMenuException();
        }
        else {
            this.produtos.add(produto);
        }
    }
}
