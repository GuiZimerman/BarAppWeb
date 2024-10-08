package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    @Query("SELECT f FROM Funcionario f WHERE f.usuario.login = :login AND f.usuario.senha = :senha")
    Funcionario validaFuncionario(@Param("login") String login, @Param("senha") String senha);
    
    boolean existsByNomeAndCargo_NivelCargo(String nome, String nivelCargo);
    
}
