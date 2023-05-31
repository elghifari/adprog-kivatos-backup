package com.service.kivatos.services.impl;

import com.service.kivatos.dto.NasabahDto;
import com.service.kivatos.models.Ewallet;
import com.service.kivatos.models.Nasabah;
import com.service.kivatos.models.Rekening;
import com.service.kivatos.models.User;
import com.service.kivatos.repository.EwalletRepository;
import com.service.kivatos.repository.NasabahRepository;
import com.service.kivatos.repository.RekeningRepository;
import com.service.kivatos.repository.UserRepository;
import com.service.kivatos.services.NasabahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NasabahServiceImpl implements NasabahService {
    @Autowired
    private NasabahRepository nasabahRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RekeningRepository rekeningRepository;

    @Autowired
    private EwalletRepository ewalletRepository;

    public Nasabah registerNasabah(NasabahDto nasabahDto) {
        User user = userRepository.findByEmail(nasabahDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + nasabahDto.getEmail()));

        if (user != null) {
            throw new RuntimeException("Email sudah digunakan");
        }

        Nasabah nasabah = new Nasabah();
        nasabah.setUser(user);
        nasabah.setNamaLengkap(nasabahDto.getNamaLengkap());
        nasabah.setTanggalLahir(nasabahDto.getTanggalLahir());
        nasabah.setAlamat(nasabahDto.getAlamat());
        nasabahRepository.save(nasabah);

        return nasabah;
    }

    @Override
    public List<Rekening> getRekeningByNasabahId(Long nasabahId) {
        return rekeningRepository.findByNasabahId(nasabahId);
    }

    @Override
    public List<Ewallet> getEwalletByNasabahId(Long nasabahId) {
        return ewalletRepository.findByNasabahId(nasabahId);
    }


}
