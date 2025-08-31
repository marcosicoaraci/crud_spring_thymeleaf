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
@Table(name = "transacao")
public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transacao")
    private Integer idTransacao;
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;
    @JoinColumn(name = "id_conta_destino", referencedColumnName = "id_conta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta idContaDestino;
    @JoinColumn(name = "id_conta_origem", referencedColumnName = "id_conta")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Conta idContaOrigem;

}
