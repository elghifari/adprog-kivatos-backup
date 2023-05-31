package com.service.kivatos.payload.dto;

import com.service.kivatos.dto.NasabahDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RekeningProfileSaldoDto {
    private Long id;
    private String jenisRekening;
    private NasabahDto nasabah;
    private BigDecimal saldo;

    public RekeningProfileSaldoDto(Long id, String jenisRekening, NasabahDto nasabah, BigDecimal saldo) {
        this.id = id;
        this.jenisRekening = jenisRekening;
        this.nasabah = nasabah;
        this.saldo = saldo;
    }

    // getters and setters
}
