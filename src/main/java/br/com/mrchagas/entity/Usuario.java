package br.com.mrchagas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

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
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username")
    private String username;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "role")
    private String role;
}
