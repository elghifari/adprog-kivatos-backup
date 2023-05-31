package com.service.kivatos.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEwalletResponse implements Serializable {
    private static final long serialVersionUID = -1507835620776968188L;
    private Long id;
    private Long ewalletId;
    private String namaNasabah;
    private BigDecimal saldoNasabah;
    private String tipeTransaksi;
    private String jenisTransaksi;
    private BigDecimal jumlah;
    private List<TransactionDetailResponse> detailTransaksiList;
}
