package com.service.kivatos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ewallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ewallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nasabah_id")
    private Nasabah nasabah;

    private String jenisEwallet;

    private BigDecimal saldo;

    private BigDecimal biayaAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
