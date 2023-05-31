package com.service.kivatos.converter;

import com.service.kivatos.models.Rekening;
import com.service.kivatos.models.Transaksi;
import com.service.kivatos.models.TransaksiDetail;
import com.service.kivatos.payload.dto.RekeningDto;
import com.service.kivatos.payload.response.TransactionDetailResponse;
import com.service.kivatos.payload.response.TransactionRekeningResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RekeningConverter {

    public RekeningDto convertToDto(Rekening rekening) {RekeningDto rekeningDto = new RekeningDto();
        rekeningDto.setId(rekening.getId());
        rekeningDto.setJenisRekening(rekening.getJenisRekening());
        rekeningDto.setSaldo(rekening.getSaldo());
        return rekeningDto;
    }

    public List<RekeningDto> toRekeningDtoList(List<Rekening> rekeningList) {
        return rekeningList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}

