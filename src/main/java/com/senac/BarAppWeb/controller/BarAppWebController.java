package com.senac.BarAppWeb.controller;


import com.senac.BarAppWeb.model.Cliente;
import com.senac.BarAppWeb.model.Conta;
import com.senac.BarAppWeb.model.Produto;
import com.senac.BarAppWeb.model.Venda;
import com.senac.BarAppWeb.model.VendaProduto;
import com.senac.BarAppWeb.service.ClienteService;
import com.senac.BarAppWeb.service.ContaService;
import com.senac.BarAppWeb.service.ProdutoService;
import com.senac.BarAppWeb.service.VendaProdutoService;
import com.senac.BarAppWeb.service.VendaService;
import java.util.Date;
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
    @Autowired
    VendaProdutoService vendaProdutoService;
    
    @GetMapping("/")
    public String mostraInicial() {
        return "redirect:/atendimento";
    }
    
    @GetMapping("/atendimento")
    public String mostraAtendimento(Model model){
        List<Conta> listaContasAbertas = contaService.buscarTodasContasAbertas();
        boolean nenhumaConta = listaContasAbertas.isEmpty();
        model.addAttribute("nenhumaConta", nenhumaConta);
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
        Produto produto = produtoService.buscarProdutoPorId(id);
        
        if (produto != null) {
            model.addAttribute("produto", produto);
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
    
    @GetMapping("/estoque/deletar")
    public String deletarProduto(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.excluirProduto(id);
            redirectAttributes.addFlashAttribute("mensagem", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir o produto.");
        }
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

    
    @GetMapping("/fazerPedido/{id}")
    public String mostrarFazerPedido(@PathVariable int id, Model model){
        Conta conta = contaService.findById(id);
        Cliente cliente = conta.getCliente();
        
        List<Produto> listaProdutos = produtoService.buscarTodosProdutos();
        
        model.addAttribute("cliente", cliente);
        model.addAttribute("conta", conta);
        model.addAttribute("listaProdutos", listaProdutos);
        return "fazerPedido";
    }
    
     @PostMapping("/salvarPedido")
    public String salvarPedido(
        @RequestParam("contaId") int contaId, 
        @RequestParam("produtoId") int produtoId, 
        @RequestParam("quantidade") int quantidade, 
        RedirectAttributes redirectAttributes) {

        Conta conta = contaService.findById(contaId);
        Produto produto = produtoService.buscarProdutoPorId(produtoId);

        if (produto.getQtdEstoque() < quantidade) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Quantidade indisponível em estoque.");
            return "redirect:/fazerPedido/" + contaId;
        }

        produto.setQtdEstoque(produto.getQtdEstoque() - quantidade);
        produtoService.atualizarProduto(produto);

        double valorVenda = quantidade * produto.getPrecoProduto();

        conta.setValorTotal(conta.getValorTotal() + valorVenda);
        contaService.atualizarConta(conta);

        Venda novaVenda = new Venda();
        novaVenda.setDataVenda(new Date());
        novaVenda.setConta(conta);
        novaVenda.setSubTotal(valorVenda);
        vendaService.cadastrarVenda(novaVenda);

        VendaProduto vendaProduto = new VendaProduto();
        vendaProduto.setVenda(novaVenda);
        vendaProduto.setProduto(produto);
        vendaProduto.setQuantidade(quantidade);
        vendaProdutoService.salvarVendaProduto(vendaProduto);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Pedido realizado com sucesso!");
        return "redirect:/fazerPedido/" + contaId;
    }
    
    @GetMapping("/finalizar/{id}")
    public String finalizarConta(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Conta conta = contaService.findById(id);
            if (conta != null) {
                conta.setStatusPagamento(true);
                contaService.atualizarConta(conta);
                redirectAttributes.addFlashAttribute("successMessage", "Conta finalizada com sucesso.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Conta não encontrada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao finalizar a conta.");
        }
        return "redirect:/atendimento"; 

    }
    
}

