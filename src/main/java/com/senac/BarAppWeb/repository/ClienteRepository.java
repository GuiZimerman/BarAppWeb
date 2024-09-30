package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
