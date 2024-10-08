package com.senac.BarAppWeb.service;

import com.senac.BarAppWeb.model.Funcionario;
import com.senac.BarAppWeb.repository.FuncionarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
    @Autowired
    FuncionarioRepository funcionarioRepository;
    
    public Funcionario validaFuncionario(String login, String senha){
        return funcionarioRepository.validaFuncionario(login, senha);
    }
    
    public boolean existsFuncionario(String nome, String cargo) {
        return funcionarioRepository.existsByNomeAndCargo_NivelCargo(nome, cargo);
    }
    public boolean validacaoSessaoFunc(Funcionario funcionario) {
        boolean validaFunc = false;
        if(funcionario != null) {
            String nomeFunc = funcionario.getNome();
            String cargo = funcionario.getCargo().getNivelCargo();
            validaFunc = existsFuncionario(nomeFunc,cargo);
        }
        if(validaFunc){
            return true;
        } else {
            return false;
        }
    }
}
