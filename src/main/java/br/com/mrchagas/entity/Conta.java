package br.com.mrchagas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author marcos
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "conta")
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    private Integer idConta;
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "numero_conta")
    private String numeroConta;
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "agencia")
    private String agencia;
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo_conta")
    private String tipoConta;
    @NotNull
    @Column(name = "saldo")
    private BigDecimal saldo;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente idCliente;
    @Column(name = "status_conta")
    private Boolean statusConta;


}
