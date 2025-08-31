package br.com.mrchagas.service;

import br.com.mrchagas.dto.request.ContaRequestDTO;
import br.com.mrchagas.dto.response.ContaResponseDTO;
import br.com.mrchagas.entity.Conta;
import br.com.mrchagas.exceptions.ObjetoNaoEncontradoException;
import br.com.mrchagas.interfaces.IContaService;
import br.com.mrchagas.repository.ContaRepository;
import org.modelmapper.ModelMapper;
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
        var resposta = contaRepository.listar(pageable);
        if (resposta.isEmpty()) {
            throw new ObjetoNaoEncontradoException("A lista não possui itens");
        }else{
            for (Conta conta : resposta) {
                ContaResponseDTO clienteResponse = modelMapper.map(conta, ContaResponseDTO.class);
                responseList.add(clienteResponse);
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
            contaRepository.deleteById(id);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao excluir objeto: "+e);
        }

    }

    private ContaResponseDTO saveOrUpdate(ContaRequestDTO requestDTO){
        try {
            var conta = modelMapper.map(requestDTO, Conta.class);
            conta.setSaldo(new BigDecimal(0));
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
}
