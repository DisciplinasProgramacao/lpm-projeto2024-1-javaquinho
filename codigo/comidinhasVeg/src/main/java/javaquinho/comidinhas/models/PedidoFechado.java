package javaquinho.comidinhas.models;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
@Entity
public class PedidoFechado extends Pedido<MenuFechado> {
    private static int MAXIMO_ITENS_PESSOA = 2;

    public int getMaximoItens() {
        return this.getRequisicao().getQuantPessoas() * MAXIMO_ITENS_PESSOA;
    }

    @Override
    public double getSomarTotal() {
        return this.getRequisicao().getQuantPessoas() * 32;
    }

    @Override
    public void addProduto(Produto produto) throws NaoExisteMenuException, ProdutoNaoExisteNoMenuException, LimiteProdutosException{
        if (this.getMenu() == null){
            throw new NaoExisteMenuException();
        }
        else if (this.getProdutos().size() >= MAXIMO_ITENS_PESSOA){
            throw new LimiteProdutosException(MAXIMO_ITENS_PESSOA);
        }
        else if (!this.getMenu().produtoExiste(produto)){
            throw new ProdutoNaoExisteNoMenuException();
        }
        else {
            this.produtos.add(produto);
        }
    }
}
