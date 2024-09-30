package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
