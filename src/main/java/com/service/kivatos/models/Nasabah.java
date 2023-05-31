package com.service.kivatos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "nasabah")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Nasabah {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String namaLengkap;

    private LocalDate tanggalLahir;

    private String alamat;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
