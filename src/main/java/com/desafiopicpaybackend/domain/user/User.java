package com.desafiopicpaybackend.domain.user;

import com.desafiopicpaybackend.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    //Transforma uma informação (UserDTO) em uma entity (User)
    public User(UserDTO userDTO){
        this.name = userDTO.name();
        this.document = userDTO.document();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.balance = userDTO.balance();
    }
}
