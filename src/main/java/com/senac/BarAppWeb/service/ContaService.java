package com.senac.BarAppWeb.service;

import com.senac.BarAppWeb.model.Conta;
import com.senac.BarAppWeb.repository.ContaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    @Autowired
    ContaRepository contaRepository;
    
   public Conta criarConta (Conta novaConta) {
       novaConta.setIdConta(null);
       contaRepository.save(novaConta);
       
       return novaConta;
   }
   
   public Conta atualizarConta(Integer contaId, Conta contaAtualizada) {
        Conta contaExistente = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + contaId));

        contaExistente.setCliente(contaAtualizada.getCliente());
        contaExistente.setStatusPagamento(contaAtualizada.isStatusPagamento());
        contaExistente.setValorTotal(contaAtualizada.getValorTotal());

        contaRepository.save(contaExistente);

        return contaExistente;
    }
   
   public Conta findById(Integer contaId) {
       return contaRepository.findById(contaId).orElse(null);
   }
   
   public List<Conta> findAll() {
       return contaRepository.findAll();
   }
}
