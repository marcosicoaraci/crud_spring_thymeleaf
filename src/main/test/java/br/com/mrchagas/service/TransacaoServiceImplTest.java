package br.com.mrchagas.service;

import br.com.mrchagas.dto.request.ClienteRequestDTO;
import br.com.mrchagas.dto.request.ContaRequestDTO;
import br.com.mrchagas.dto.request.TransacaoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TransacaoServiceImplTest {

    @Autowired
    private ClienteServiceImpl clienteServiceMock;

    @Autowired
    private ContaServiceImpl contaServiceMock;

    @Autowired
    private TransacaoServiceImpl transacaoServiceMock;

    @BeforeEach
    void setup(){

        var cliente = new ClienteRequestDTO();
        cliente.setNome("Marty Mcfly");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        var clienteResponse = this.clienteServiceMock.salvar(cliente);

        var conta = new ContaRequestDTO();
        conta.setIdCliente(clienteResponse.getIdCliente());
        conta.setSaldo(new BigDecimal(10000));
        conta.setTipoConta("CORRENTE");
        conta.setAgencia("11111");
        conta.setNumeroConta("2222222");

        var contaResponse = this.contaServiceMock.salvar(conta);

        var transacao = new TransacaoRequestDTO();
        transacao.setValor(new BigDecimal(100));
        transacao.setTipo("DEPOSITO");
        transacao.setDataHora(new Date());
        transacao.setIdContaOrigem(contaResponse.getIdConta());

        this.transacaoServiceMock.salvar(transacao);

    }
    @Test
    public void getUmTest() {
        var cliente = new ClienteRequestDTO();
        cliente.setNome("Marty Mcfly");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        var clienteResponse = this.clienteServiceMock.salvar(cliente);

        var conta = new ContaRequestDTO();
        conta.setIdCliente(clienteResponse.getIdCliente());
        conta.setSaldo(new BigDecimal(10000));
        conta.setTipoConta("CORRENTE");
        conta.setAgencia("11111");
        conta.setNumeroConta("2222222");

        var contaResponse = this.contaServiceMock.salvar(conta);

        var transacao = new TransacaoRequestDTO();
        transacao.setValor(new BigDecimal(100));
        transacao.setTipo("DEPOSITO");
        transacao.setDataHora(new Date());
        transacao.setIdContaOrigem(contaResponse.getIdConta());

        var responseTransacao = this.transacaoServiceMock.salvar(transacao);

        var transacaoGet = this.transacaoServiceMock.getUm(responseTransacao.getIdTransacao());
        Integer idTansacao = transacaoGet.getIdTransacao();
        assertEquals(idTansacao,transacaoGet.getIdTransacao());
    }
    @Test
    public void listarTodosTest() {
        var pageable = PageRequest.of(0,10);
        var transacaoList = this.transacaoServiceMock.listaPaginada(pageable);
        assertEquals(1, transacaoList.getTotalPages());
    }
    @Test
    public void salvarTest() {
        var cliente = new ClienteRequestDTO();
        cliente.setNome("Marty Mcfly");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        var clienteResponse = this.clienteServiceMock.salvar(cliente);

        var conta = new ContaRequestDTO();
        conta.setIdCliente(clienteResponse.getIdCliente());
        conta.setSaldo(new BigDecimal(10000));
        conta.setTipoConta("CORRENTE");
        conta.setAgencia("11111");
        conta.setNumeroConta("2222222");

        var contaResponse = this.contaServiceMock.salvar(conta);

        var transacao = new TransacaoRequestDTO();
        transacao.setValor(new BigDecimal(100));
        transacao.setTipo("DEPOSITO");
        transacao.setDataHora(new Date());
        transacao.setIdContaOrigem(contaResponse.getIdConta());

        var responseTransacao = this.transacaoServiceMock.salvar(transacao);

        assertEquals("DEPOSITO",responseTransacao.getTipo());
    }


    @Test
    void excluirTest() {
        this.transacaoServiceMock.excluir(1);
        assertThrows(
                Exception.class,
                () -> this.transacaoServiceMock.getUm(1)
        );
    }
}


