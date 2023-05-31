package com.service.kivatos.payload.dto;

import com.service.kivatos.dto.NasabahDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EwalletProfileSaldoDto {
    private Long id;
    private String jenisEwallet;
    private NasabahDto nasabah;
    private BigDecimal saldo;

    public EwalletProfileSaldoDto(Long id, String jenisEwallet, NasabahDto nasabah, BigDecimal saldo) {
        this.id = id;
        this.jenisEwallet = jenisEwallet;
        this.nasabah = nasabah;
        this.saldo = saldo;
    }
}
