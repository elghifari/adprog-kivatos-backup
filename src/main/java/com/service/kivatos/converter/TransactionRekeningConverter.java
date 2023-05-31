package com.service.kivatos.converter;

import com.service.kivatos.models.Transaksi;
import com.service.kivatos.models.TransaksiDetail;
import com.service.kivatos.payload.response.TransactionDetailResponse;
import com.service.kivatos.payload.response.TransactionRekeningResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionRekeningConverter {

    public TransactionRekeningResponse convertToResponse(Transaksi transaksi) {
        TransactionRekeningResponse transactionRekeningResponse = new TransactionRekeningResponse();

        transactionRekeningResponse.setId(transaksi.getId());
        transactionRekeningResponse.setRekeningId(transaksi.getRekening().getId());
        transactionRekeningResponse.setNamaNasabah(transaksi.getRekening().getNasabah().getNamaLengkap());
        transactionRekeningResponse.setSaldoNasabah(transaksi.getRekening().getSaldo());
        transactionRekeningResponse.setTipeTransaksi(transaksi.getTipeTransaksi());
        transactionRekeningResponse.setJenisTransaksi(transaksi.getJenisTransaksi());
        transactionRekeningResponse.setJumlah(transaksi.getJumlah());

        List<TransactionDetailResponse> transactionDetailResponses = new ArrayList<>();
        for (TransaksiDetail transactionDetail : transaksi.getTransaksiDetails()) {
            TransactionDetailResponse transactionDetailResponse = new TransactionDetailResponse();
            transactionDetailResponse.setDeskripsi(transactionDetail.getDeskripsi());
            transactionDetailResponse.setCreatedAt(transactionDetail.getCreatedAt());
            transactionDetailResponses.add(transactionDetailResponse);
        }
        transactionRekeningResponse.setDetailTransaksiList(transactionDetailResponses);

        return transactionRekeningResponse;
    }

    public List<TransactionRekeningResponse> toTransactionDtoList(List<Transaksi> transaksiList) {
        return transaksiList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}

