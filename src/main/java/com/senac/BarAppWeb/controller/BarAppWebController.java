package com.senac.BarAppWeb.controller;


import com.senac.BarAppWeb.model.Cliente;
import com.senac.BarAppWeb.model.Conta;
import com.senac.BarAppWeb.service.ClienteService;
import com.senac.BarAppWeb.service.ContaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BarAppWebController {
    
    @Autowired
    ContaService contaService;
    @Autowired
    ClienteService clienteService;
    
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
    
    @GetMapping("/cadastroCliente")
    public String mostrarCadastroCliente(Model model) {
        return "cadastroCliente";
    }
    
    @PostMapping("/cadastroCliente")
    public String fazerCadastro(@ModelAttribute("cliente") Cliente novoCliente, Model model) {
       clienteService.criarCliente(novoCliente);
       model.addAttribute("mensagem", "Cliente cadastrado com sucesso!");
       return "atendimento";
    }
    
    @GetMapping("/abrirConta")
    public String abrirConta() {
        return "abrirConta";
    }
    
    @GetMapping("/abrirConta/{nome}")
    public String buscarNomeCliente(@PathVariable("buscaNome") String nome, Model model) {
        return "abrirConta";
    }
}

