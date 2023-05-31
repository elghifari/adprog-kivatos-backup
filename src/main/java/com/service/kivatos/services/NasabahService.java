package com.service.kivatos.services;

import com.service.kivatos.dto.NasabahDto;
import com.service.kivatos.models.Ewallet;
import com.service.kivatos.models.Nasabah;
import com.service.kivatos.models.Rekening;

import java.util.List;

public interface NasabahService {

    Nasabah registerNasabah(NasabahDto nasabahDto);

    List<Rekening> getRekeningByNasabahId(Long nasabahId);

    List<Ewallet> getEwalletByNasabahId(Long nasabahId);
}
