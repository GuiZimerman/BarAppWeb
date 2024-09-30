package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Conta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContaRepository  extends JpaRepository<Conta, Integer>{
    List<Conta> findByStatusPagamentoFalse();
}
