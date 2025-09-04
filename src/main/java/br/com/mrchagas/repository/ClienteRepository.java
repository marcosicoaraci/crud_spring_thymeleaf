package br.com.mrchagas.repository;

import br.com.mrchagas.entity.Cliente;
import br.com.mrchagas.interfaces.ClienteContaDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    @Query("select c from Cliente c WHERE c.statusCliente = true ORDER BY c.idCliente DESC")
    List<Cliente> listar(Pageable pageable);

    @Query(value = "SELECT cl.nome as nome, cl.email as email, cl.cpf as cpf, " +
            "co.agencia as agencia, co.numero_conta as numeroConta, co.saldo as saldo " +
            "FROM Cliente cl " +
            "INNER JOIN Conta co ON cl.id_cliente = co.id_cliente " +
            "WHERE cl.cpf = :usuarioLogado",
            nativeQuery = true)
    ClienteContaDTO buscarPorLogin(String usuarioLogado);
}
