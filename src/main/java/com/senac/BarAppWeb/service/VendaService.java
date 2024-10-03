package com.senac.BarAppWeb.service;

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
    
}
