package br.com.mrchagas.controller;

import br.com.mrchagas.dto.request.TransacaoRequestDTO;
import br.com.mrchagas.dto.response.ContaResponseDTO;
import br.com.mrchagas.dto.response.TransacaoResponseDTO;
import br.com.mrchagas.entity.Conta;
import br.com.mrchagas.interfaces.ClienteContaDTO;
import br.com.mrchagas.service.ClienteServiceImpl;
import br.com.mrchagas.service.ContaServiceImpl;
import br.com.mrchagas.service.TransacaoServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {
    private final TransacaoServiceImpl transacaoSrv;
    private final ClienteServiceImpl clienteSrv;
    private final ContaServiceImpl contaSrv;

    public TransacaoController(TransacaoServiceImpl transacaoSrv, ClienteServiceImpl clienteSrv, ContaServiceImpl contaSrv) {
        this.transacaoSrv = transacaoSrv;
        this.clienteSrv = clienteSrv;
        this.contaSrv = contaSrv;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page,
                         Model model,
                         Authentication authentication) {
        int pageSize = 4;
        var pageable = PageRequest.of(page, pageSize);
        var transacoes = transacaoSrv.listaPaginada(pageable);

        ClienteContaDTO usuarioLogado = this.clienteSrv.buscarPorLogin(authentication.getName());
        var contas = listarContas();

        model.addAttribute("contas", contas);
        model.addAttribute("conta", new ContaResponseDTO());
        model.addAttribute("usuarioLogado", usuarioLogado);

        model.addAttribute("transacoes", transacoes.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transacoes.getTotalPages());

        model.addAttribute("transacao", new TransacaoRequestDTO());
        return "transacoes/listar";
    }

    private List<Conta> listarContas(){
        return this.contaSrv.listarTodos();
//        var pageable = PageRequest.of(0, 100);
//        return this.contaSrv.listarTodos(pageable);
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("transacao", new TransacaoRequestDTO());
        return "transacoes/cadastro";
    }

    @PostMapping("depositar")
    public String depositar(@ModelAttribute TransacaoRequestDTO transacao,Authentication authentication) {
        var usuarioLogado = this.clienteSrv.buscarPorLogin(authentication.getName());
        var conta = this.contaSrv.obterPorAgenciaeConta(usuarioLogado.getAgencia(),usuarioLogado.getNumeroConta());
        conta.setSaldo(conta.getSaldo().add(transacao.getValor()));
        transacao.setTipo("DEPOSITO");
        transacao.setIdContaOrigem(conta.getIdConta());
        transacaoSrv.salvar(transacao);
        return "redirect:/transacoes";
    }

    @PostMapping("efetuar-saque")
    public String efetuarSaque(@ModelAttribute TransacaoRequestDTO transacao,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            var usuarioLogado = this.clienteSrv.buscarPorLogin(authentication.getName());
            var conta = this.contaSrv.obterPorAgenciaeConta(usuarioLogado.getAgencia(),usuarioLogado.getNumeroConta());
            int resultado = conta.getSaldo().compareTo(transacao.getValor());
            if (resultado >= 0) {
                conta.setSaldo(conta.getSaldo().subtract(transacao.getValor()));
            }else{
                throw new Exception("O Saldo é insuficiente para essa transação");
            }
            transacao.setTipo("SAQUE");
            transacao.setIdContaOrigem(conta.getIdConta());
            transacaoSrv.salvar(transacao);
            return "redirect:/transacoes";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("erroMensagem", "Atenção: " + e.getMessage());
            return "redirect:/transacoes";        }
    }

    @PostMapping("efetuar-transferencia")
    public String efetuarTransferencia(@ModelAttribute TransacaoRequestDTO transacao,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            var usuarioLogado = this.clienteSrv.buscarPorLogin(authentication.getName());
            var conta = this.contaSrv.obterPorAgenciaeConta(usuarioLogado.getAgencia(),usuarioLogado.getNumeroConta());
            int resultado = conta.getSaldo().compareTo(transacao.getValor());
            if (resultado >= 0) {
                conta.setSaldo(conta.getSaldo().subtract(transacao.getValor()));
            }else{
                throw new Exception("O Saldo é insuficiente para essa transação");
            }
            transacao.setTipo("TRANSFERENCIA");
            transacao.setIdContaOrigem(conta.getIdConta());
            transacaoSrv.salvar(transacao);
            return "redirect:/transacoes";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("erroMensagem", "Atenção: " + e.getMessage());
            return "redirect:/transacoes";        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        TransacaoResponseDTO transacao = transacaoSrv.getUm(id);
        model.addAttribute("transacao", transacao);
        return "transacoes/cadastro";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Integer id, @ModelAttribute TransacaoRequestDTO transacao) {
        transacao.setIdTransacao(id);
        transacaoSrv.salvar(transacao);
        return "redirect:/transacoes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        transacaoSrv.excluir(id);
        return "redirect:/transacoes";
    }

}
