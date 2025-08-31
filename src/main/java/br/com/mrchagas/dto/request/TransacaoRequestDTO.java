package br.com.mrchagas.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransacaoRequestDTO {
    private Integer idTransacao;
    private Date dataHora;
    private String tipo;
    private BigDecimal valor;
    private Integer idContaDestino;
    private Integer idContaOrigem;

}
