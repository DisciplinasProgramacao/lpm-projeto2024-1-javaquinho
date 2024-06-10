package javaquinho.comidinhas.models;

import java.util.ArrayList;
import java.util.List;



public class Restaurante {

    private List<Produto> menu;

    public Restaurante() {
        this.menu = new ArrayList<>();
    }

   
    public void adicionarProdutoAoMenu(Produto produto) {
        this.menu.add(produto);
    }

    
    public List<Produto> exibirMenu() {
        return new ArrayList<>(menu);
    }



    public void adicionarProdutoARequisicao(Requisicao requisicao, Produto produto) {
        if (menu.contains(produto)) {
            requisicao.adicionarProduto(produto);
        } else {
            throw new IllegalArgumentException("Produto não disponível no menu.");
        }
    }
    
}
