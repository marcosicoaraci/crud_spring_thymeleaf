package br.com.mrchagas.service;

import br.com.mrchagas.dto.request.TransacaoRequestDTO;
import br.com.mrchagas.dto.response.TransacaoResponseDTO;
import br.com.mrchagas.entity.Transacao;
import br.com.mrchagas.exceptions.ObjetoNaoEncontradoException;
import br.com.mrchagas.interfaces.ITransacaoService;
import br.com.mrchagas.repository.TransacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class TransacaoServiceImpl implements ITransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }


    @Override
    public TransacaoResponseDTO getUm(Integer id) {
        try {
            var conta = this.transacaoRepository.findById(id).get();
            return modelMapper.map(conta, TransacaoResponseDTO.class);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao recuperar item "+e);
        }

    }

    @Override
    public List<TransacaoResponseDTO> listarTodos(Pageable pageable) {
        List<TransacaoResponseDTO> responseList = new ArrayList<>();
        var resposta = transacaoRepository.listar(pageable);
        if (resposta.isEmpty()) {
            return responseList;
        }else{
            for (Transacao transacao : resposta) {
                TransacaoResponseDTO clienteResponse = new TransacaoResponseDTO();
                clienteResponse.setIdTransacao(transacao.getIdTransacao());
                clienteResponse.setTipo(transacao.getTipo());
                clienteResponse.setValor(transacao.getValor());
                clienteResponse.setIdContaOrigem(Integer.valueOf(transacao.getIdContaOrigem().getNumeroConta()));
                if (nonNull(transacao.getIdContaDestino())) {
                    clienteResponse.setIdContaDestino(Integer.valueOf(transacao.getIdContaDestino().getNumeroConta()));
                }else {
                    clienteResponse.setIdContaDestino(0);
                }
                responseList.add(clienteResponse);
            }
        }
        return responseList;
    }

    public Page<Transacao> listaPaginada(Pageable pageable) {
        Page<Transacao> resposta = this.transacaoRepository.listaPaginada(pageable);
        if (resposta.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return resposta;
    }

    @Override
    public TransacaoResponseDTO salvar(TransacaoRequestDTO requestDTO) {
        return saveOrUpdate(requestDTO);
    }

    @Override
    public TransacaoResponseDTO atualizar(TransacaoRequestDTO requestDTO) {
        return saveOrUpdate(requestDTO);
    }

    @Override
    public void excluir(Integer id) {
        try {
            transacaoRepository.deleteById(id);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao excluir objeto: "+e);
        }

    }

    private TransacaoResponseDTO saveOrUpdate(TransacaoRequestDTO requestDTO){
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            var transacao = modelMapper.map(requestDTO, Transacao.class);
            transacaoRepository.save(transacao);
            return modelMapper.map(transacao, TransacaoResponseDTO.class);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao cadastrar objeto "+e);
        }

    }
}
