package com.service.kivatos.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RekeningDto {
    private Long id;
    private String jenisRekening;
    private BigDecimal saldo;
}

