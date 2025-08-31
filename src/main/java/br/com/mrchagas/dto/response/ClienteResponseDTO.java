package br.com.mrchagas.dto.response;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Integer idCliente;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;

}
