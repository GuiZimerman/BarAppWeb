package com.senac.BarAppWeb.repository;

import com.senac.BarAppWeb.model.Venda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VendaRepository extends JpaRepository<Venda, Integer> {
    @Query("SELECT v.idVenda, p.nomeProduto, vp.quantidade, v.subTotal "
            + "FROM Venda v "
            + "JOIN VendaProduto vp ON v.idVenda = vp.venda.id "
            + "JOIN Produto p ON vp.produto.id = p.id "
            + "WHERE v.conta.idConta = :idConta")
    List<Object[]> findVendaDetalhesByContaId(@Param("idConta") int idConta);


    
}
