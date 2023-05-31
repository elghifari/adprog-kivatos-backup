package com.service.kivatos.services.impl;

import com.service.kivatos.models.Ewallet;
import com.service.kivatos.repository.EwalletRepository;
import com.service.kivatos.services.EwalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EwalletServiceImpl implements EwalletService {
    @Autowired
    private EwalletRepository ewalletRepository;

    @Transactional
    public void saveAllEwallet(List<Ewallet> ewalletList) {
        // remove ID property from ewalletList
        ewalletList.forEach(ewallet -> ewallet.setId(null));

        // save all unique records
        List<Ewallet> uniqueEwalletList = ewalletList.stream()
                .distinct()
                .collect(Collectors.toList());
        ewalletRepository.saveAll(uniqueEwalletList);
    }
}
