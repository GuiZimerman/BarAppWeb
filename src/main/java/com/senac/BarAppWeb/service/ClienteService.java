package com.senac.BarAppWeb.service;

import com.senac.BarAppWeb.model.Cliente;
import com.senac.BarAppWeb.model.Conta;
import com.senac.BarAppWeb.repository.ClienteRepository; 
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ContaService contaService;
    
    public Cliente criarCliente(Cliente novoCliente) {
        return clienteRepository.save(novoCliente);
    }
    
    public Cliente atualizarCliente(int idCliente, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + idCliente));

        clienteExistente.setNomeCliente(clienteAtualizado.getNomeCliente());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        
        return clienteRepository.save(clienteExistente);
    }
    
    public Cliente buscarPorId(int idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }
    
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeClienteContainingIgnoreCase(nome);
    }
    
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }
    
    public List<Cliente> listarClientesDisponiveis() {
        List<Cliente> clientesDisponiveis = new ArrayList<>();
        List<Cliente >listaTodosClientes = listarTodosClientes();
        List<Conta> listaContasAbertas = contaService.buscarTodasContasAbertas();
        
        for (Cliente clinte : listaTodosClientes) {
            boolean clienteAssociado = false;
            for (Conta conta : listaContasAbertas) {
                if (conta.getCliente().getIdCliente() == clinte.getIdCliente()) {
                    clienteAssociado = true;
                    break;
                }
            }
            if (!clienteAssociado) {
                clientesDisponiveis.add(clinte);
            }
        }
        return clientesDisponiveis;
    }
    
    
}
