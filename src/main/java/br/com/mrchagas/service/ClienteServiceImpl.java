package br.com.mrchagas.service;

import br.com.mrchagas.dto.request.ClienteRequestDTO;
import br.com.mrchagas.dto.response.ClienteResponseDTO;
import br.com.mrchagas.entity.Cliente;
import br.com.mrchagas.entity.Conta;
import br.com.mrchagas.exceptions.ObjetoNaoEncontradoException;
import br.com.mrchagas.interfaces.ClienteContaDTO;
import br.com.mrchagas.interfaces.IClienteService;
import br.com.mrchagas.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaServiceImpl contaSrv;
    private final ModelMapper modelMapper = new ModelMapper();

    public ClienteServiceImpl(ClienteRepository clienteRepository, ContaServiceImpl contaSrv) {
        this.clienteRepository = clienteRepository;
        this.contaSrv = contaSrv;
    }

    @Override
    public ClienteResponseDTO getUm(Integer id) {
        try {
            var cliente = this.clienteRepository.findById(id).get();
            return modelMapper.map(cliente, ClienteResponseDTO.class);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao recuperar item "+e);
        }

    }

    @Override
    public List<ClienteResponseDTO> listarTodos(Pageable pageable) {
        List<ClienteResponseDTO> responseList = new ArrayList<>();
        var resposta = clienteRepository.listar(pageable);
        if (resposta.isEmpty()) {
            return responseList;
        }else{
            for (Cliente cliente : resposta) {
                ClienteResponseDTO clienteResponse = modelMapper.map(cliente, ClienteResponseDTO.class);
                responseList.add(clienteResponse);
            }
        }
        return responseList;

    }

    @Override
    public ClienteResponseDTO salvar(ClienteRequestDTO requestDTO) {
        try {
            return saveOrUpdate(requestDTO);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao parsear a data "+e);
        }
    }

    @Override
    public ClienteResponseDTO atualizar(ClienteRequestDTO requestDTO) {
        try {
            return saveOrUpdate(requestDTO);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao parsear a data "+e);
        }
    }

    @Override
    public void excluir(Integer id) {
        try {
            Cliente cli = this.clienteRepository.findById(id).get();
            cli.setStatusCliente(false);
            this.clienteRepository.save(cli);
            Conta conta = this.contaSrv.obterPorIdCliente(id);
            if (nonNull(conta)) {
                this.contaSrv.excluir(conta.getIdConta());
            }
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao excluir objeto: "+e);
        }

    }

    private ClienteResponseDTO saveOrUpdate(ClienteRequestDTO requestDTO){
        try {
            var cliente = modelMapper.map(requestDTO, Cliente.class);
            cliente.setStatusCliente(true);
            clienteRepository.save(cliente);
            return modelMapper.map(cliente, ClienteResponseDTO.class);
        }catch (Exception e){
            throw new ObjetoNaoEncontradoException("Erro ao cadastrar objeto "+e);
        }

    }

    @Override
    public ClienteContaDTO buscarPorLogin(String usuariologado) {
        return clienteRepository.buscarPorLogin(usuariologado);
    }
}
