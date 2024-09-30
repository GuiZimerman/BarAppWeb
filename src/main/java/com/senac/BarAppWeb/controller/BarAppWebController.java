package com.senac.BarAppWeb.controller;


import com.senac.BarAppWeb.model.Conta;
import com.senac.BarAppWeb.service.ContaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BarAppWebController {
    
    @Autowired
    ContaService contaService;
    
//    @GetMapping("/")
//    public String mostraInicial() {
//        return "redirect:/index";
//    }
    
    @GetMapping("/atendimento")
    public String mostraAtendimento(Model model){
        List<Conta> listaContasAbertas = contaService.findAll();
        model.addAttribute("listaContaAbertas", listaContasAbertas);
        return "atendimento";
    }
    
}

