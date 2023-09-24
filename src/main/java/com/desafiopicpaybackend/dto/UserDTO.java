package com.desafiopicpaybackend.dto;

import com.desafiopicpaybackend.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(
        String name,
        String document,
        BigDecimal balance,
        String email,
        String password,
        UserType userType
) {
}
