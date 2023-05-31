package com.service.kivatos.controllers;

import com.service.kivatos.converter.*;
import com.service.kivatos.dto.NasabahDto;
import com.service.kivatos.models.Ewallet;
import com.service.kivatos.models.Nasabah;
import com.service.kivatos.models.Rekening;
import com.service.kivatos.models.Transaksi;
import com.service.kivatos.payload.dto.*;
import com.service.kivatos.payload.response.TransactionRekeningEwalletResponse;
import com.service.kivatos.payload.response.TransactionRekeningResponse;
import com.service.kivatos.repository.EwalletRepository;
import com.service.kivatos.repository.NasabahRepository;
import com.service.kivatos.repository.RekeningRepository;
import com.service.kivatos.services.NasabahService;
import com.service.kivatos.services.TransactionService;
import com.service.kivatos.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/nasabah")
public class NasabahController {

    @Autowired
    private NasabahRepository nasabahRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRekeningConverter transactionRekeningConverter;

    @Autowired
    private RekeningRepository rekeningRepository;

    @Autowired
    private EwalletRepository ewalletRepository;

    @Autowired
    private RekeningConverter rekeningConverter;

    @Autowired
    private EwalletConverter ewalletConverter;

    @Autowired
    private NasabahConverter nasabahConverter;

    @Autowired
    private NasabahService nasabahService;

    @Autowired
    private TransactionRekeningEwalletConverter transactionRekeningEwalletConverter;

    @GetMapping("/account/{nasabahId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AccountNasabahDto>> getNasabahById(@PathVariable Long nasabahId) {
        try {
            Nasabah nasabah = nasabahRepository.findById(nasabahId)
                    .orElseThrow(() -> new EntityNotFoundException("Nasabah with id " + nasabahId + " not found"));

            List<Rekening> rekeningList = nasabahService.getRekeningByNasabahId(nasabahId);
            List<RekeningDto> rekeningDtoList = Collections.emptyList();
            if (rekeningList != null && !rekeningList.isEmpty()) {
                rekeningDtoList = rekeningConverter.toRekeningDtoList(rekeningList);
            }

            List<Ewallet> ewalletList = nasabahService.getEwalletByNasabahId(nasabahId);
            List<EwalletDto> ewalletDtoList = Collections.emptyList();
            if (ewalletList != null && !ewalletList.isEmpty()) {
                ewalletDtoList = ewalletConverter.toEwalletDtoList(ewalletList);
            }

            AccountNasabahDto nasabahDto = new AccountNasabahDto(nasabah, rekeningDtoList, ewalletDtoList);

            return ResponseEntity.ok(new ApiResponse<>("true", "Success", nasabahDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>("false", e.getMessage(), null));
        }
    }

    @GetMapping("/saldo/rekening/{rekeningId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RekeningProfileSaldoDto>> getRekeningProfileAndSaldoById(@PathVariable Long rekeningId) {
        try {
            Rekening rekening = rekeningRepository.findById(rekeningId)
                    .orElseThrow(() -> new EntityNotFoundException("Rekening with id " + rekeningId + " not found"));
            NasabahDto nasabah = nasabahConverter.convertToDto(rekening.getNasabah());
            RekeningDto rekeningDto = rekeningConverter.convertToDto(rekening);
            BigDecimal saldo = rekening.getSaldo();
            return ResponseEntity.ok(new ApiResponse<>("true", "Success", new RekeningProfileSaldoDto(rekeningDto.getId(), rekeningDto.getJenisRekening(), nasabah, saldo)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>("false", e.getMessage(), null));
        }
    }

    @GetMapping("/saldo/ewallet/{ewalletId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EwalletProfileSaldoDto>> getEwalletProfileAndSaldoById(@PathVariable Long ewalletId) {
        try {
            Ewallet ewallet = ewalletRepository.findById(ewalletId)
                    .orElseThrow(() -> new EntityNotFoundException("Ewallet with id " + ewalletId + " not found"));
            NasabahDto nasabah = nasabahConverter.convertToDto(ewallet.getNasabah());
            EwalletDto ewalletDto = ewalletConverter.convertToDto(ewallet);
            BigDecimal saldo = ewallet.getSaldo();
            return ResponseEntity.ok(new ApiResponse<>("true", "Success", new EwalletProfileSaldoDto(ewalletDto.getId(), ewalletDto.getJenisEwallet(), nasabah, saldo)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>("false", e.getMessage(), null));
        }
    }

    @GetMapping("/history/{nasabahId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<TransactionRekeningEwalletResponse>>> getTransaksiByNasabahId(@PathVariable Long nasabahId) {
        List<Transaksi> transaksiList = transactionService.getTransaksiByNasabahId(nasabahId);
        if (transaksiList == null || transaksiList.isEmpty()) {
            ApiResponse<List<TransactionRekeningEwalletResponse>> response = new ApiResponse<>("error", "Data transaksi tidak ditemukan", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            List<TransactionRekeningEwalletResponse> responseList = transactionRekeningEwalletConverter.toTransactionDtoList(transaksiList);
            ApiResponse<List<TransactionRekeningEwalletResponse>> response = new ApiResponse<>("success", "Data transaksi berhasil ditemukan", responseList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


}
