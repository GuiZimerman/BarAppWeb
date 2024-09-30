package com.senac.BarAppWeb.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BarAppWebController {
    
    @GetMapping("/")
    public String mostraInicial() {
        return "redirect:/index";
    }
    
    @GetMapping("/atendimento")
    public String mostraAtendimento(Model model){
        return "atendimento";
    }
    
}

