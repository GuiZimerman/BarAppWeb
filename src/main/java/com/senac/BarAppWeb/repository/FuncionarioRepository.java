package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
