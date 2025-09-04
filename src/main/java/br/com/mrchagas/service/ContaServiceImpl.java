package br.com.mrchagas.service;

import br.com.mrchagas.dto.request.ContaRequestDTO;
import br.com.mrchagas.dto.response.ContaResponseDTO;
import br.com.mrchagas.entity.Conta;
import br.com.mrchagas.exceptions.ObjetoNaoEncontradoException;
import br.com.mrchagas.interfaces.IContaService;
import br.com.mrchagas.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaServiceImpl implements IContaService {

    private final ContaRepository contaRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ContaServiceImpl(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }


    @Override
    public ContaResponseDTO getUm(Integer id) {
        try {
            var conta = this.contaRepository.findById(id).get();
            return modelMapper.map(conta, ContaResponseDTO.class);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao recuperar item "+e);
        }

    }

    @Override
    public List<ContaResponseDTO> listarTodos(Pageable pageable) {
        List<ContaResponseDTO> responseList = new ArrayList<>();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        var resposta = contaRepository.listar(pageable);
        if (resposta.isEmpty()) {
            throw new ObjetoNaoEncontradoException("A lista não possui itens");
        }else{
            for (Conta conta : resposta) {
                ContaResponseDTO contaResponse = new ContaResponseDTO();
                contaResponse.setIdConta(conta.getIdConta());
                contaResponse.setNumeroConta(conta.getNumeroConta());
                contaResponse.setAgencia(conta.getAgencia());
                contaResponse.setIdCliente(conta.getIdCliente().getIdCliente());
                contaResponse.setTipoConta(conta.getTipoConta());
                contaResponse.setSaldo(conta.getSaldo());
                responseList.add(contaResponse);
            }
        }
        return responseList;
    }

    @Override
    public ContaResponseDTO salvar(ContaRequestDTO requestDTO) {
        return saveOrUpdate(requestDTO);
    }

    @Override
    public ContaResponseDTO atualizar(ContaRequestDTO requestDTO) {
        return saveOrUpdate(requestDTO);
    }

    @Override
    public void excluir(Integer id) {
        try {
            var conta = this.contaRepository.findById(id);
            if (conta.isPresent()) {
                Conta objConta = conta.get();
                objConta.setStatusConta(false);
                contaRepository.save(objConta);
            }
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao excluir objeto: "+e);
        }

    }

    private ContaResponseDTO saveOrUpdate(ContaRequestDTO requestDTO){
        try {
            var conta = modelMapper.map(requestDTO, Conta.class);
            conta.setSaldo(new BigDecimal(0));
            conta.setStatusConta(true);
            contaRepository.save(conta);
            return modelMapper.map(conta, ContaResponseDTO.class);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao cadastrar objeto "+e);
        }

    }

    @Override
    public Conta obterPorAgenciaeConta(String agencia, String conta) {
        return this.contaRepository.obterPorAgenciaeConta(agencia,conta);
    }

    @Override
    public Conta obterPorIdCliente(Integer idCliente) {
        return this.contaRepository.obterPorIdCliente(idCliente);
    }

    @Override
    public List<Conta> listarTodos() {
        var pageable = PageRequest.of(0,100);
        return (List<Conta>) this.contaRepository.listar(pageable);
    }
}
