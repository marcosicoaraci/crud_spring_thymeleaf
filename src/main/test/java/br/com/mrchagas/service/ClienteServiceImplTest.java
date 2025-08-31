package br.com.mrchagas.service;

import br.com.mrchagas.dto.request.ClienteRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ClienteServiceImplTest {

    @Autowired
    private ClienteServiceImpl clienteServiceMock;
    @BeforeEach
    void setup(){

        var cliente = new ClienteRequestDTO();
        cliente.setNome("Marty Mcfly");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        this.clienteServiceMock.salvar(cliente);
    }
    @Test
    public void getUmTest() {
        var cliente = new ClienteRequestDTO();
        cliente.setNome("Marty Mcfly");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        var responseInsert = this.clienteServiceMock.salvar(cliente);

        var cli = this.clienteServiceMock.getUm(responseInsert.getIdCliente());
        assertEquals("88888800211",cli.getCpf());
    }
    @Test
    public void listarTodosTest() {
        var pageable = PageRequest.of(0,10);
        var clienteList = this.clienteServiceMock.listarTodos(pageable);
        assertFalse(clienteList.isEmpty());
        assertTrue(clienteList.stream().allMatch(c -> c.getIdCliente() != null), "Todos os clientes devem ter ID");
    }

    @Test
    public void salvarTest() {
        var cliente = new ClienteRequestDTO();
        cliente.setNome("Biff Tannen");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        this.clienteServiceMock.salvar(cliente);

        var clienteResponse = this.clienteServiceMock.salvar(cliente);
        assertEquals("88888800211",clienteResponse.getCpf());
    }
    @Test
    public void updateTest() {
        var cliente = new ClienteRequestDTO();
        cliente.setNome("Biff Tannen");
        cliente.setEmail("backtothefuture@gmail.com");
        cliente.setCpf("88888800211");
        cliente.setEndereco("xxxxxxx");
        cliente.setTelefone("999999999");
        cliente.setIdCliente(1);
        var response = this.clienteServiceMock.salvar(cliente);


        assertEquals("88888800211",response.getCpf());
    }
    @Test
    void excluirTest() {
        this.clienteServiceMock.excluir(1);
        assertThrows(
                Exception.class,
                () -> this.clienteServiceMock.getUm(1)
        );
    }
}


