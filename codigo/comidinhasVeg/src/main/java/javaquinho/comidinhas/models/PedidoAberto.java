package javaquinho.comidinhas.models;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
@Entity
public class PedidoAberto extends Pedido<MenuAberto> {
    public PedidoAberto(int qntPessoas){
        super(qntPessoas);
    }

    public PedidoAberto(){}
    
    @Override
    public void setMenu(MenuAberto menu) throws MenuInvalidoException{
        try {
            this.menu = menu;
        } catch(Exception e){
            throw new MenuInvalidoException(MenuAberto.class.getName());
        }
    }

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
