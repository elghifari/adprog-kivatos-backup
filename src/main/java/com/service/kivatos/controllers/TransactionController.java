package com.service.kivatos.controllers;

import com.service.kivatos.converter.TransactionEwalletConverter;
import com.service.kivatos.converter.TransactionRekeningConverter;
import com.service.kivatos.dto.TransaksiDto;
import com.service.kivatos.models.Transaksi;
import com.service.kivatos.payload.response.TransactionEwalletResponse;
import com.service.kivatos.payload.response.TransactionRekeningResponse;
import com.service.kivatos.services.TransactionService;
import com.service.kivatos.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRekeningConverter transactionRekeningConverter;

    @Autowired
    private TransactionEwalletConverter transactionEwalletConverter;


    @PostMapping("/rekening/{rekeningId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createTransaksiRekening(@PathVariable Long rekeningId, @RequestBody TransaksiDto transaksiDto) throws Exception {
        Transaksi transaksi = transactionService.createTransaksiRekening(rekeningId, transaksiDto);
        TransactionRekeningResponse response = transactionRekeningConverter.convertToResponse(transaksi);
        return ResponseEntity.ok(new ApiResponse("true", "Transaksi rekening berhasil dibuat", response));
    }

    @PostMapping("/ewallet/{ewalletId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createTransaksiEwallet(@PathVariable Long ewalletId, @RequestBody TransaksiDto transaksiDto) throws Exception {
        Transaksi transaksi = transactionService.createTransaksiEwallet(ewalletId, transaksiDto);
        TransactionEwalletResponse response = transactionEwalletConverter.convertToResponse(transaksi);
        return ResponseEntity.ok(new ApiResponse("true", "Transaksi ewallet berhasil dibuat", response));
    }

}
