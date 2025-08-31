package br.com.mrchagas.interfaces;

import java.math.BigDecimal;

public interface ClienteContaDTO {

    String getNome();
    String getEmail();
    String getCpf();
    String getAgencia();
    String getNumeroConta();
    BigDecimal getSaldo();
}
