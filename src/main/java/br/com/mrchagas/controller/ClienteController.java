package br.com.mrchagas.controller;

import br.com.mrchagas.dto.request.ClienteRequestDTO;
import br.com.mrchagas.dto.response.ClienteResponseDTO;
import br.com.mrchagas.service.ClienteServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteSrv;

    public ClienteController(ClienteServiceImpl clienteSrv) {
        this.clienteSrv = clienteSrv;
    }

    // Listar
    @GetMapping
    public String listar(Model model) {
        var pageable = PageRequest.of(0,10);
        model.addAttribute("clientes", clienteSrv.listarTodos(pageable));
        return "clientes/listar";
    }

    // Formulário de cadastro
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new ClienteRequestDTO());
        return "clientes/cadastro";
    }

    // Salvar nova pessoa
    @PostMapping
    public String salvar(@ModelAttribute ClienteRequestDTO cliente) {
        clienteSrv.salvar(cliente);
        return "redirect:/clientes";
    }

    // Formulário de edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        ClienteResponseDTO cliente = clienteSrv.getUm(id);
        model.addAttribute("cliente", cliente);
        return "clientes/cadastro";
    }

    // Atualizar pessoa
    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id, @ModelAttribute ClienteRequestDTO cliente) {
        cliente.setIdCliente(id);
        clienteSrv.salvar(cliente);
        return "redirect:/clientes";
    }

    // Excluir pessoa
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        clienteSrv.excluir(id);
        return "redirect:/clientes";
    }
}
