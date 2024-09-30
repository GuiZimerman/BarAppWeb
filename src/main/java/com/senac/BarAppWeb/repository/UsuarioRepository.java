package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository< Usuario, Integer> {
    
}
