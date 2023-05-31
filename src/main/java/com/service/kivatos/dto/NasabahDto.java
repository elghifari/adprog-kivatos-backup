package com.service.kivatos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NasabahDto {

    private String email;
    private String namaLengkap;
    private String password;
    private String username;
    private String phoneNumber;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate tanggalLahir;
    private String alamat;
}
