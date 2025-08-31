package br.com.mrchagas.service;

import br.com.mrchagas.dto.request.ClienteRequestDTO;
import br.com.mrchagas.dto.request.ContaRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ContaServiceImplTest {

    @Autowired
    private ClienteServiceImpl clienteServiceMock;
    @Autowired
    private ContaServiceImpl contaServiceMock;
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
        conta.setSaldo(new BigDecimal(0));
        conta.setTipoConta("CORRENTE");
        conta.setAgencia("11111");
        conta.setNumeroConta("2222222");

        this.contaServiceMock.salvar(conta);
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
        conta.setSaldo(new BigDecimal(0));
        conta.setTipoConta("CORRENTE");
        conta.setAgencia("11111");
        conta.setNumeroConta("2222222");

        var responseConta = this.contaServiceMock.salvar(conta);

        Integer idConta = responseConta.getIdConta();
        var contaResponse = this.contaServiceMock.getUm(responseConta.getIdConta());
        assertEquals(idConta,contaResponse.getIdConta());
    }
    @Test
    public void listarTodosTest() {
        var pageable = PageRequest.of(0,10);
        var contaList = this.contaServiceMock.listarTodos(pageable);
        assertEquals(1, contaList.size());
    }
    @Test
    public void salvarTest() {
        var conta = new ContaRequestDTO();

        var cliente = new ClienteRequestDTO();
        cliente.setNome("Marty Mcfly");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        var cliResponse = this.clienteServiceMock.salvar(cliente);

        var clienteResponse = this.clienteServiceMock.getUm(cliResponse.getIdCliente());
        conta.setIdCliente(clienteResponse.getIdCliente());
        conta.setSaldo(new BigDecimal(0));
        conta.setTipoConta("CORRENTE");
        conta.setAgencia("11112");
        conta.setNumeroConta("2222223");

        this.contaServiceMock.salvar(conta);

        var contaResponse = this.contaServiceMock.salvar(conta);
        assertEquals("2222223",contaResponse.getNumeroConta());
    }
    @Test
    public void updateTest() {
        var cliente = new ClienteRequestDTO();
        cliente.setNome("Marty Mcfly");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        var cliResponse = this.clienteServiceMock.salvar(cliente);

        var clienteResponse = this.clienteServiceMock.getUm(cliResponse.getIdCliente());

        var conta = new ContaRequestDTO();
        conta.setIdConta(clienteResponse.getIdCliente());
        conta.setTipoConta("POUPANCA");

        var contaResponse = this.contaServiceMock.salvar(conta);
        assertEquals("POUPANCA",contaResponse.getTipoConta());
    }
    @Test
    void excluirTest() {
        this.contaServiceMock.excluir(1);
        assertThrows(
                Exception.class,
                () -> this.contaServiceMock.getUm(1)
        );
    }
}


