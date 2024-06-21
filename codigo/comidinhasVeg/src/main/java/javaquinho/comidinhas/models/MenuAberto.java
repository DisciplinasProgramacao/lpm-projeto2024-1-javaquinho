package javaquinho.comidinhas.models;

import java.util.Set;

import jakarta.persistence.Entity;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;

@Entity
public class MenuAberto extends Menu{

    public MenuAberto(){}

    public MenuAberto(Set<Produto> produtos){
        this.setProdutos(produtos);
    }

    @Override
    public void adicionarProduto(Produto produto){
        this.produtos.add(produto);
    }

    @Override
    public void setProdutos(Set<Produto> produtos){
            this.produtos = produtos;
    }
    
}
