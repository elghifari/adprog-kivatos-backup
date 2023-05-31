package com.service.kivatos.converter;

import com.service.kivatos.models.Transaksi;
import com.service.kivatos.models.TransaksiDetail;
import com.service.kivatos.payload.response.TransactionDetailResponse;
import com.service.kivatos.payload.response.TransactionRekeningEwalletResponse;
import com.service.kivatos.payload.response.TransactionRekeningResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionRekeningEwalletConverter {

    public TransactionRekeningEwalletResponse convertToResponse(Transaksi transaksi) {
        TransactionRekeningEwalletResponse transactionRekeningEwalletResponse = new TransactionRekeningEwalletResponse();

        transactionRekeningEwalletResponse.setId(transaksi.getId());
        if(transaksi.getRekening()!=null){
            transactionRekeningEwalletResponse.setRekeningId(transaksi.getRekening().getId());
            transactionRekeningEwalletResponse.setNamaNasabah(transaksi.getRekening().getNasabah().getNamaLengkap());
            transactionRekeningEwalletResponse.setSaldoNasabah(transaksi.getRekening().getSaldo());
        }else{
            transactionRekeningEwalletResponse.setEwalletId(transaksi.getEwallet().getId());
            transactionRekeningEwalletResponse.setNamaNasabah(transaksi.getEwallet().getNasabah().getNamaLengkap());
            transactionRekeningEwalletResponse.setSaldoNasabah(transaksi.getEwallet().getSaldo());
        }


        transactionRekeningEwalletResponse.setTipeTransaksi(transaksi.getTipeTransaksi());
        transactionRekeningEwalletResponse.setJenisTransaksi(transaksi.getJenisTransaksi());
        transactionRekeningEwalletResponse.setJumlah(transaksi.getJumlah());

        List<TransactionDetailResponse> transactionDetailResponses = new ArrayList<>();
        for (TransaksiDetail transactionDetail : transaksi.getTransaksiDetails()) {
            TransactionDetailResponse transactionDetailResponse = new TransactionDetailResponse();
            transactionDetailResponse.setDeskripsi(transactionDetail.getDeskripsi());
            transactionDetailResponse.setCreatedAt(transactionDetail.getCreatedAt());
            transactionDetailResponses.add(transactionDetailResponse);
        }
        transactionRekeningEwalletResponse.setDetailTransaksiList(transactionDetailResponses);

        return transactionRekeningEwalletResponse;
    }

    public List<TransactionRekeningEwalletResponse> toTransactionDtoList(List<Transaksi> transaksiList) {
        return transaksiList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}

