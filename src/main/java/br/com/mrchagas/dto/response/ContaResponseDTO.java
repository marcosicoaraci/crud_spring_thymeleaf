package br.com.mrchagas.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ContaResponseDTO {
    private Integer idConta;
    private String numeroConta;
    private String agencia;
    private String tipoConta;
    private BigDecimal saldo;
    private Date dataAbertura;
    private Integer idCliente;

}
