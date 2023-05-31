package com.service.kivatos.converter;

import com.service.kivatos.models.Ewallet;
import com.service.kivatos.models.Rekening;
import com.service.kivatos.payload.dto.EwalletDto;
import com.service.kivatos.payload.dto.RekeningDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EwalletConverter {

    public EwalletDto convertToDto(Ewallet ewallet) {EwalletDto ewalletDto = new EwalletDto();
        ewalletDto.setId(ewalletDto.getId());
        ewalletDto.setJenisEwallet(ewallet.getJenisEwallet());
        ewalletDto.setSaldo(ewallet.getSaldo());
        return ewalletDto;
    }

    public List<EwalletDto> toEwalletDtoList(List<Ewallet> ewalletList) {
        return ewalletList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}

