package com.service.kivatos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaksi_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransaksiDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaksi_id")
    private Transaksi transaksi;

    private String deskripsi;

    private LocalDateTime createdAt;
}
