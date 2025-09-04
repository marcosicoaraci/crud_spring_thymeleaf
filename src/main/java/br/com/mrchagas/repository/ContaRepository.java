package br.com.mrchagas.repository;

import br.com.mrchagas.entity.Conta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends CrudRepository<Conta, Integer> {

    @Query("select c from Conta c WHERE c.statusConta = true ORDER BY c.idConta DESC")
    List<Conta> listar(Pageable pageable);

    @Query("select c from Conta c WHERE c.agencia = :agencia AND c.numeroConta = :conta")
    Conta obterPorAgenciaeConta(String agencia,String conta);

    @Query("select c from Conta c WHERE c.idCliente.idCliente = :idCliente AND c.statusConta = true")
    Conta obterPorIdCliente(Integer idCliente);

}
