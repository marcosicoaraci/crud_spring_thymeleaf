package br.com.mrchagas.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ClienteRequestDTO {
    private Integer idCliente;
    private String nome;
    private String cpf;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;
    private String endereco;
    private String telefone;
    private String email;

}
