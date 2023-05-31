package com.service.kivatos.payload.dto;

import com.service.kivatos.models.Nasabah;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountNasabahDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private List<RekeningDto> rekeningList;
    private List<EwalletDto> ewalletList;

    public AccountNasabahDto(Nasabah nasabah, List<RekeningDto> rekeningDtoList, List<EwalletDto> ewalletDtoList) {
        this.id = nasabah.getId();
        this.fullName = nasabah.getNamaLengkap();
        this.dateOfBirth = nasabah.getTanggalLahir();
        this.address = nasabah.getAlamat();
        this.rekeningList = rekeningDtoList;
        this.ewalletList = ewalletDtoList;
    }
}
