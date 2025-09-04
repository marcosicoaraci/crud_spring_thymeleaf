package br.com.mrchagas.interfaces;

import br.com.mrchagas.dto.request.ContaRequestDTO;
import br.com.mrchagas.dto.response.ContaResponseDTO;
import br.com.mrchagas.entity.Conta;

import java.util.List;

public interface IContaService extends IGenericService<ContaRequestDTO, ContaResponseDTO>{
    Conta obterPorAgenciaeConta(String agencia, String conta);
    Conta obterPorIdCliente(Integer idCliente);
    List<Conta> listarTodos();
}
