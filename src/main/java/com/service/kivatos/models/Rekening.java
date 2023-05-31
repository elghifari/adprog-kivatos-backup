package com.service.kivatos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rekening")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rekening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nasabah_id")
    private Nasabah nasabah;

    private String jenisRekening;

    private BigDecimal saldo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
