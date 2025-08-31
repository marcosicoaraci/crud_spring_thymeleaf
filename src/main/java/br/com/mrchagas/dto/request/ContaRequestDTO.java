package br.com.mrchagas.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContaRequestDTO {
    private Integer idConta;
    private String numeroConta;
    private String agencia;
    private String tipoConta;
    private BigDecimal saldo;
    private Integer idCliente;

}
