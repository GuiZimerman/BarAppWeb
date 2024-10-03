package com.senac.BarAppWeb.service;

import com.senac.BarAppWeb.model.Venda;
import com.senac.BarAppWeb.repository.VendaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {
    @Autowired
    VendaRepository vendaRepository;
    
    public List<Object[]> findVendaDetalhesByContaId(int idConta) {
        return vendaRepository.findVendaDetalhesByContaId(idConta);
    }
    
    public Venda cadastrarVenda(Venda venda) {
        return vendaRepository.save(venda);
    }

    public Venda atualizarVenda(Venda venda) {
        if (vendaRepository.existsById(venda.getIdVenda())) {
            return vendaRepository.save(venda); // Atualiza a venda
        } else {
            throw new IllegalArgumentException("Venda não encontrada.");
        }
    }

    public void excluirVenda(int idVenda) {
        if (vendaRepository.existsById(idVenda)) {
            vendaRepository.deleteById(idVenda);
        } else {
            throw new IllegalArgumentException("Venda não encontrada.");
        }
    }

    public Venda findById(int idVenda) {
        return vendaRepository.findById(idVenda).orElse(null);
    }

    
}
