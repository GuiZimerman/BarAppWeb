package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContaRepository  extends JpaRepository< Conta,Integer>{
    
}
