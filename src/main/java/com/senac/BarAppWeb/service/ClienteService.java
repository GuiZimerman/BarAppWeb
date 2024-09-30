package com.senac.BarAppWeb.service;

import com.senac.BarAppWeb.model.Cliente;
import com.senac.BarAppWeb.repository.ClienteRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
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
}
