package com.service.kivatos.converter;

import com.service.kivatos.models.Transaksi;
import com.service.kivatos.models.TransaksiDetail;
import com.service.kivatos.payload.response.TransactionDetailResponse;
import com.service.kivatos.payload.response.TransactionEwalletResponse;
import com.service.kivatos.payload.response.TransactionRekeningResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionEwalletConverter {

    public TransactionEwalletResponse convertToResponse(Transaksi transaksi) {
        TransactionEwalletResponse transactionEwalletResponse = new TransactionEwalletResponse();

        transactionEwalletResponse.setId(transaksi.getId());
        transactionEwalletResponse.setEwalletId(transaksi.getEwallet().getId());
        transactionEwalletResponse.setNamaNasabah(transaksi.getEwallet().getNasabah().getNamaLengkap());
        transactionEwalletResponse.setSaldoNasabah(transaksi.getEwallet().getSaldo());
        transactionEwalletResponse.setTipeTransaksi(transaksi.getTipeTransaksi());
        transactionEwalletResponse.setJenisTransaksi(transaksi.getJenisTransaksi());
        transactionEwalletResponse.setJumlah(transaksi.getJumlah());

        List<TransactionDetailResponse> transactionDetailResponses = new ArrayList<>();
        for (TransaksiDetail transactionDetail : transaksi.getTransaksiDetails()) {
            TransactionDetailResponse transactionDetailResponse = new TransactionDetailResponse();
            transactionDetailResponse.setDeskripsi(transactionDetail.getDeskripsi());
            transactionDetailResponse.setCreatedAt(transactionDetail.getCreatedAt());
            transactionDetailResponses.add(transactionDetailResponse);
        }
        transactionEwalletResponse.setDetailTransaksiList(transactionDetailResponses);

        return transactionEwalletResponse;
    }

    public List<TransactionEwalletResponse> toTransactionDtoList(List<Transaksi> transaksiList) {
        return transaksiList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}

