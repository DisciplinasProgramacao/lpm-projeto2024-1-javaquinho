package javaquinho.comidinhas.models;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;

@Entity
public class PedidoFechado extends Pedido<MenuFechado> {
    private static int MAXIMO_ITENS_PESSOA = 2;

    public PedidoFechado(int qntPessoas) {
        super(qntPessoas);
    }

    public PedidoFechado() {
    }

     @Override
    public void setMenu(MenuFechado menu) throws MenuInvalidoException{
        try {
            this.menu = menu;
        } catch(Exception e){
            throw new MenuInvalidoException(MenuAberto.class.getName());
        }
    }

    public int getMaximoItens() {
        return this.qntPessoas * MAXIMO_ITENS_PESSOA;
    }

    @Override
    public double getSomarTotal() {
        return this.qntPessoas * 32;
    }

    @Override
    public void addProduto(Produto produto)
            throws NaoExisteMenuException, ProdutoNaoExisteNoMenuException, LimiteProdutosException {
        if (this.getMenu() == null) {
            throw new NaoExisteMenuException();
        } else if (this.getProdutos().size() >= MAXIMO_ITENS_PESSOA) {
            throw new LimiteProdutosException(MAXIMO_ITENS_PESSOA);
        } else if (!this.getMenu().produtoExiste(produto)) {
            throw new ProdutoNaoExisteNoMenuException();
        } else {
            this.produtos.add(produto);
        }
    }
}
