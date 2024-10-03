package com.senac.BarAppWeb.controller;


import com.senac.BarAppWeb.model.Cliente;
import com.senac.BarAppWeb.model.Conta;
import com.senac.BarAppWeb.service.ClienteService;
import com.senac.BarAppWeb.service.ContaService;
import com.senac.BarAppWeb.service.VendaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BarAppWebController {
    
    @Autowired
    ContaService contaService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    VendaService vendaService;
    
    @GetMapping("/")
    public String mostraInicial() {
        return "redirect:/atendimento";
    }
    
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
       return "redirect:/abrirConta";
    }
    
    @GetMapping("/abrirConta")
    public String abrirConta(@RequestParam(value = "buscaNome", required = false) String nome, Model model) {
        List<Cliente> listaClientes;

        if (nome != null && !nome.isEmpty()) {
            listaClientes = clienteService.buscarPorNome(nome);
            if(listaClientes.isEmpty()) {
                listaClientes = clienteService.listarTodosClientes();
                model.addAttribute("mensagem", "Nenhum cliente com esse nome encontrado.");
            }
        } else {
            listaClientes = clienteService.listarTodosClientes();
        }
        
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("listaClientes", listaClientes);
        return "abrirConta";
    }
    
    @PostMapping("/abrirConta/salvar")
    public String abrirConta(@RequestParam("clienteId") Integer idCliente, Model model) {
        if (idCliente == null) {
            model.addAttribute("mensagem", "Nenhum cliente selecionado.");
            return "redirect:/abrirConta"; 
        }

        Cliente clienteSelecionado = clienteService.buscarPorId(idCliente);
        if (clienteSelecionado != null) {
            Conta novaConta = new Conta();
            novaConta.setCliente(clienteSelecionado);
            novaConta.setStatusPagamento(false);
            novaConta.setValorTotal(0);
            contaService.criarConta(novaConta);
            model.addAttribute("mensagem", "Conta aberta com sucesso!");
        } else {
            model.addAttribute("mensagem", "Falha na abertura de conta.");
        }

        return "redirect:/atendimento";
    }

    
    @GetMapping("/estoque")
    public String mostrarEstoque() {
        return "estoque";
    }
    
    @GetMapping("/contaDetalhada") 
    public String mostrarContaDetalhada() {
        return "contaDetalhada";
    }
    
    @GetMapping("/contaDetalhada/{id}")
    public String detalharConta(@PathVariable int id, Model model) {
        Conta conta = contaService.findById(id);
//        List<Object[]> detalhesVenda = vendaService.findVendaDetalhesByContaId(id);

        model.addAttribute("conta", conta);
//        model.addAttribute("detalhesVenda", detalhesVenda);
        return "contaDetalhada";
    }

    
    @GetMapping("/fazerPedido")
    public String mostrarFazerPedido(){
        return "fazerPedido";
    }
}

