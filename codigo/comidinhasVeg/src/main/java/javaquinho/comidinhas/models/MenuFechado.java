package javaquinho.comidinhas.models;

import java.util.Set;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;

@Entity
public class MenuFechado extends Menu{
    private static int MAXIMO_ITENS = 5;

    public MenuFechado(){}

    public MenuFechado(Set<Produto> produtos) throws LimiteProdutosException{
        this.setProdutos(produtos);
    }

    @Override
    public void setProdutos(Set<Produto> produtos) throws LimiteProdutosException{
        if (produtos.size() > MAXIMO_ITENS){
            throw new LimiteProdutosException(MAXIMO_ITENS);
        }
        else {
            this.produtos = produtos;
        }
    }

    @Override
    public void adicionarProduto(Produto produto) throws LimiteProdutosException{
        if (this.getProdutos().size() >= MAXIMO_ITENS){
            throw new LimiteProdutosException(MAXIMO_ITENS);
        }
        else {
            this.produtos.add(produto);
        }
    }
    
}
