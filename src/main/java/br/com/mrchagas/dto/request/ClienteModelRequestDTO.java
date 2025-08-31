package br.com.mrchagas.dto.request;

import lombok.Data;

@Data
public class ClienteModelRequestDTO {
    private Integer idCliente;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String endereco;
    private String telefone;
    private String email;

}
