package com.senac.BarAppWeb.service;

import com.senac.BarAppWeb.model.VendaProduto;
import com.senac.BarAppWeb.repository.VendaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaProdutoService {
    @Autowired
    VendaProdutoRepository vendaProdutoRepository;
    
    public VendaProduto salvarVendaProduto(VendaProduto vendaProduto){
        return vendaProdutoRepository.save(vendaProduto);
    }
}
