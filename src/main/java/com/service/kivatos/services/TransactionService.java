package com.service.kivatos.services;

import com.service.kivatos.dto.TransaksiDto;
import com.service.kivatos.models.Transaksi;

import java.util.List;

public interface TransactionService {

    Transaksi createTransaksiRekening(Long rekeningId, TransaksiDto transaksiDto) throws Exception;
    Transaksi createTransaksiEwallet(Long ewalletId, TransaksiDto transaksiDto) throws Exception;
    List<Transaksi> getTransaksiByNasabahId(Long rekeningId);
}
