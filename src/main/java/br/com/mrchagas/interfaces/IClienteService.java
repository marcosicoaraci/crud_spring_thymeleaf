package br.com.mrchagas.interfaces;

import br.com.mrchagas.dto.request.ClienteRequestDTO;
import br.com.mrchagas.dto.response.ClienteResponseDTO;

public interface IClienteService extends IGenericService<ClienteRequestDTO, ClienteResponseDTO> {

    ClienteContaDTO buscarPorLogin(String usuariologado);
}
