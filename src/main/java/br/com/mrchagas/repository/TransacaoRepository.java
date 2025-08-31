package br.com.mrchagas.repository;

import br.com.mrchagas.entity.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends CrudRepository<Transacao, Integer> {
    @Query("select t from Transacao t ORDER BY t.idTransacao DESC")
    List<Transacao> listar(Pageable pageable);

    @Query("select t from Transacao t ORDER BY t.idTransacao DESC")
    Page<Transacao> listaPaginada(Pageable pageable);

}
