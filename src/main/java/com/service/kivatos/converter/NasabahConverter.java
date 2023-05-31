package com.service.kivatos.converter;

import com.service.kivatos.dto.NasabahDto;
import com.service.kivatos.models.Nasabah;
import com.service.kivatos.models.Rekening;
import com.service.kivatos.payload.dto.RekeningDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NasabahConverter {

    public NasabahDto convertToDto(Nasabah nasabah) {
        NasabahDto nasabahDto = new NasabahDto();
        nasabahDto.setAlamat(nasabah.getAlamat());
        nasabahDto.setEmail(nasabah.getUser().getEmail());
        nasabahDto.setNamaLengkap(nasabah.getNamaLengkap());
        nasabahDto.setTanggalLahir(nasabah.getTanggalLahir());
        nasabahDto.setPhoneNumber(nasabah.getUser().getPhoneNumber());
        return nasabahDto;
    }

    public List<NasabahDto> toNasabahDtoList(List<Nasabah> nasabahList) {
        return nasabahList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}

