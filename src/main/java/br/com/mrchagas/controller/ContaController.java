package br.com.mrchagas.controller;

import br.com.mrchagas.dto.request.ContaRequestDTO;
import br.com.mrchagas.dto.response.ContaResponseDTO;
import br.com.mrchagas.service.ContaServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contas")
public class ContaController {

    private final ContaServiceImpl contaSrv;

    public ContaController(ContaServiceImpl contaSrv) {
        this.contaSrv = contaSrv;
    }


    @GetMapping
    public String listar(Model model) {
        var pageable = PageRequest.of(0,10);
        model.addAttribute("contas", contaSrv.listarTodos(pageable));
        return "contas/listar";
    }

    @GetMapping("/novo/{idCliente}")
    public String novo(@PathVariable Integer idCliente, Model model) {
        ContaRequestDTO conta = new ContaRequestDTO();
        conta.setIdCliente(idCliente);
        model.addAttribute("conta", conta);
        return "contas/cadastro";
    }

    @PostMapping
    public String salvar(@ModelAttribute ContaRequestDTO conta) {
        contaSrv.salvar(conta);
        return "redirect:/contas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        ContaResponseDTO conta = contaSrv.getUm(id);
        model.addAttribute("conta", conta);
        return "contas/cadastro";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id, @ModelAttribute ContaRequestDTO conta) {
        conta.setIdConta(id);
        contaSrv.salvar(conta);
        return "redirect:/contas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        contaSrv.excluir(id);
        return "redirect:/contas";
    }


}
