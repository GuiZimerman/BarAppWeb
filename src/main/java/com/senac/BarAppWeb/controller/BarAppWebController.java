package com.senac.BarAppWeb.controller;


import com.senac.BarAppWeb.model.Cliente;
import com.senac.BarAppWeb.model.Conta;
import com.senac.BarAppWeb.model.Produto;
import com.senac.BarAppWeb.service.ClienteService;
import com.senac.BarAppWeb.service.ContaService;
import com.senac.BarAppWeb.service.ProdutoService;
import com.senac.BarAppWeb.service.VendaService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BarAppWebController {
    
    @Autowired
    ContaService contaService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    VendaService vendaService;
    @Autowired
    ProdutoService produtoService;
    
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
    public String abrirConta(Model model) {

        List<Cliente > clientesDisponiveis = clienteService.listarClientesDisponiveis();
        
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("listaClientes", clientesDisponiveis);
        return "abrirConta";
    }
    
    @GetMapping("abrirConta/filtrar")
    public String filtrarClientes(@RequestParam(value = "buscaNome", required = false) String nome, Model model) {

        List<Cliente> clientesDisponiveis = clienteService.listarClientesDisponiveis();

        if (nome != null && !nome.isEmpty()) {
            clientesDisponiveis = clientesDisponiveis.stream()
                .filter(cliente -> cliente.getNomeCliente().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
            
            if (clientesDisponiveis.isEmpty()) {
                model.addAttribute("mensagem", "Nenhum cliente com esse nome disponível.");
                clientesDisponiveis = clienteService.listarClientesDisponiveis();
            }
        }
        
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("listaClientes", clientesDisponiveis);
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
    public String mostrarEstoque(Model model) {
        List<Produto> listaProdutos = produtoService.buscarTodosProdutos();
        
        model.addAttribute("listaProdutos", listaProdutos);
        return "estoque";
    }
    
    @GetMapping("/estoque/adicionar")
    public String exibirPaginaAdicionarProduto(Model model){
        Produto produto = new Produto();
        
        model.addAttribute("produto", produto);
        return "adicionarProduto";
    }
    
    @PostMapping("/estoque/adicionar")
    public String adicionarProduto(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {

        produtoService.salvarProduto(produto);

        redirectAttributes.addFlashAttribute("mensagem", "Produto adicionado com sucesso!");
        return "redirect:/estoque"; 
    }
    
    @GetMapping("/estoque/atualizar")
    public String exibirPaginaAtualizar(@RequestParam("id") int id, Model model) {
        Optional<Produto> produtoOpt = produtoService.buscarProdutoPorId(id);
        
        if (produtoOpt.isPresent()) {
            model.addAttribute("produto", produtoOpt.get());
            return "atualizarProduto"; 
        } else {
            model.addAttribute("mensagem", "Produto não encontrado!");
            return "redirect:/estoque"; 
        }
    }
    
    @PostMapping("/estoque/atualizar")
    public String atualizarProduto(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        produtoService.salvarProduto(produto); 
        redirectAttributes.addFlashAttribute("mensagem", "Produto atualizado com sucesso!");
        return "redirect:/estoque"; 
    }

    
    @GetMapping("/contaDetalhada") 
    public String mostrarContaDetalhada() {
        return "contaDetalhada";
    }
    
    @GetMapping("/contaDetalhada/{id}")
    public String detalharConta(@PathVariable int id, Model model) {
        Conta conta = contaService.findById(id);
        List<Object[]> detalhesVenda = vendaService.findVendaDetalhesByContaId(id);

        model.addAttribute("conta", conta);
        model.addAttribute("detalhesVenda", detalhesVenda);
        return "contaDetalhada";
    }

    
    @GetMapping("/fazerPedido")
    public String mostrarFazerPedido(){
        return "fazerPedido";
    }
}

