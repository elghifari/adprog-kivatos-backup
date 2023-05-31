package com.service.kivatos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaksi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rekening_id")
    private Rekening rekening;

    @ManyToOne
    @JoinColumn(name = "ewallet_id")
    private Ewallet ewallet;

    private String tipeTransaksi;
    private String jenisTransaksi;

    private BigDecimal jumlah;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "transaksi", cascade = CascadeType.ALL)
    private List<TransaksiDetail> transaksiDetails = new ArrayList<>();
}
