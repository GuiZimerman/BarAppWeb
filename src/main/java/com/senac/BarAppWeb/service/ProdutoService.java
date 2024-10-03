package com.senac.BarAppWeb.service;

import com.senac.BarAppWeb.model.Produto;
import com.senac.BarAppWeb.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Produto produto) {
        Optional<Produto> produtoExistente = produtoRepository.findById(produto.getIdProduto());
        if (produtoExistente.isPresent()) {
            return produtoRepository.save(produto);
        }
        throw new RuntimeException("Produto não encontrado para atualização.");
    }

    public void excluirProduto(int idProduto) {
        Produto produtoExistente = produtoRepository.findById(idProduto).orElse(null);
        if (produtoExistente != null) {
            produtoRepository.delete(produtoExistente);
        } else {
            throw new RuntimeException("Produto não encontrado para exclusão.");
        }
    }

    public List<Produto> buscarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(int idProduto) {
        return produtoRepository.findById(idProduto).orElse(null);
    }
}

