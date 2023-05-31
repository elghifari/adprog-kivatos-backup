package com.service.kivatos.services.impl;

import com.service.kivatos.dto.TransaksiDto;
import com.service.kivatos.models.*;
import com.service.kivatos.repository.EwalletRepository;
import com.service.kivatos.repository.RekeningRepository;
import com.service.kivatos.repository.TransaksiRepository;
import com.service.kivatos.services.EmailService;
import com.service.kivatos.services.TransactionService;
import com.service.kivatos.utils.UtilException;
import com.service.kivatos.utils.enumeration.TransactionEwalletType;
import com.service.kivatos.utils.enumeration.TransactionRekeningType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    public static final BigDecimal adminBank = BigDecimal.valueOf(2500);

    @Autowired
    private RekeningRepository rekeningRepository;

    @Autowired
    private EwalletRepository ewalletRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private EmailService emailService;


    @Override
    @Transactional
    public Transaksi createTransaksiRekening(Long rekeningId, TransaksiDto transaksiDto) throws Exception {
        Rekening rekening = rekeningRepository.findById(rekeningId)
                .orElseThrow(() -> new UtilException.ResourceNotFoundException("Rekening", "id", rekeningId));

        Transaksi transaksi = new Transaksi();
        transaksi.setRekening(rekening);
        transaksi.setTipeTransaksi(transaksiDto.getTipeTransaksi());
        transaksi.setJenisTransaksi("rekening");
        transaksi.setJumlah(transaksiDto.getJumlah());
        transaksi.setCreatedAt(LocalDateTime.now());

        TransaksiDetail transaksiDetail = new TransaksiDetail();
        transaksiDetail.setTransaksi(transaksi);
        transaksiDetail.setDeskripsi(transaksiDto.getDeskripsi());
        transaksiDetail.setCreatedAt(LocalDateTime.now());

        transaksi.getTransaksiDetails().add(transaksiDetail);
        transaksi = transaksiRepository.save(transaksi);
        BigDecimal updateSaldo = updateValueSaldoRekening(transaksiDto.getTipeTransaksi(), transaksiDto.getJumlah(), rekening.getSaldo());
        rekening.setSaldo(updateSaldo);
        rekeningRepository.save(rekening);
        String emailTo = rekening.getNasabah().getUser().getEmail();
        String userPhone = rekening.getNasabah().getUser().getPhoneNumber();
        String subject = "";
        String body = "";
        if(transaksiDto.getTipeTransaksi().equals("listrik_2304") || transaksiDto.getTipeTransaksi().equals("transportasi_2305") || transaksiDto.getTipeTransaksi().equals("education_2306")){
            subject = "Membayar " + transaksiDto.getTipeTransaksi().toUpperCase() + " dari " + rekening.getJenisRekening();
            body = String.format("Terima kasih sudah bertransaksi dengan Bank Kivatos!, Berikut adalah detail transaksi anda: %s %s sebesar %s,  Simpan email ini sebagai referensi transaksi Anda. ",
                    subject, userPhone, transaksiDto.getJumlah());
        }
        else{
            subject = "Melakukan " + transaksiDto.getTipeTransaksi().toUpperCase() + " " + rekening.getJenisRekening();
            body = String.format("Terima kasih sudah bertransaksi dengan Bank Kivatos!, Berikut adalah detail transaksi anda: %s ke %s sebesar %s,  Simpan email ini sebagai referensi transaksi Anda. ",
                    subject, userPhone, transaksiDto.getJumlah());
        }
        try {
            emailService.sendEmail(emailTo, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return transaksi;
    }

    @Override
    @Transactional
    public Transaksi createTransaksiEwallet(Long ewalletId, TransaksiDto transaksiDto) throws Exception {
        Ewallet ewallet = ewalletRepository.findById(ewalletId)
                .orElseThrow(() -> new UtilException.ResourceNotFoundException("Ewallet", "id", ewalletId));

        Transaksi transaksi = new Transaksi();
        transaksi.setEwallet(ewallet);
        transaksi.setTipeTransaksi(transaksiDto.getTipeTransaksi());
        transaksi.setJenisTransaksi("ewallet");
        transaksi.setJumlah(transaksiDto.getJumlah());
        transaksi.setCreatedAt(LocalDateTime.now());

        TransaksiDetail transaksiDetail = new TransaksiDetail();
        transaksiDetail.setTransaksi(transaksi);
        transaksiDetail.setDeskripsi(transaksiDto.getDeskripsi());
        transaksiDetail.setCreatedAt(LocalDateTime.now());

        transaksi.getTransaksiDetails().add(transaksiDetail);
        transaksi = transaksiRepository.save(transaksi);
        BigDecimal updateSaldo = updateValueSaldoEwallet(ewallet, transaksiDto.getTipeTransaksi(), transaksiDto.getJumlah(), ewallet.getSaldo());
        ewallet.setSaldo(updateSaldo);
        ewalletRepository.save(ewallet);
        String emailTo = ewallet.getNasabah().getUser().getEmail();
        String userPhone = ewallet.getNasabah().getUser().getPhoneNumber();
        String subject = transaksiDto.getTipeTransaksi().toUpperCase() + " " + ewallet.getJenisEwallet();
        String body = String.format("Terima kasih sudah bertransaksi dengan Bank Kivatos!, Berikut adalah detail transaksi anda: %s ke %s sebesar %s,  Simpan email ini sebagai referensi transaksi Anda. ",
                subject, userPhone, transaksiDto.getJumlah());
        try {
            emailService.sendEmail(emailTo, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return transaksi;
    }

    private void topUpEwallet(Long rekeningId, TransaksiDto transaksiDto) throws Exception {
        Rekening rekening = rekeningRepository.findById(rekeningId)
                .orElseThrow(() -> new UtilException.ResourceNotFoundException("Rekening", "id", rekeningId));

        Transaksi transaksi = new Transaksi();
        transaksi.setRekening(rekening);
        transaksi.setTipeTransaksi(transaksiDto.getTipeTransaksi());
        transaksi.setJenisTransaksi("rekening");
        transaksi.setJumlah(transaksiDto.getJumlah());
        transaksi.setCreatedAt(LocalDateTime.now());

        TransaksiDetail transaksiDetail = new TransaksiDetail();
        transaksiDetail.setTransaksi(transaksi);
        transaksiDetail.setDeskripsi(transaksiDto.getDeskripsi());
        transaksiDetail.setCreatedAt(LocalDateTime.now());

        transaksi.getTransaksiDetails().add(transaksiDetail);
        transaksiRepository.save(transaksi);
        BigDecimal updateSaldo = updateValueSaldoRekening(transaksiDto.getTipeTransaksi(), transaksiDto.getJumlah(), rekening.getSaldo());
        rekening.setSaldo(updateSaldo);
        rekeningRepository.save(rekening);
    }

    public BigDecimal updateValueSaldoRekening(String transactionRekeningType, BigDecimal jumlahTransaksi, BigDecimal saldoSaatIni) throws Exception {
        // Validasi input parameter
        if (transactionRekeningType == null) {
            throw new Exception("Gagal melakukan aktivitas, tipe transaksi tidak boleh null.");
        }

        BigDecimal saldoAkhir;
        if (!TransactionRekeningType.fromString(transactionRekeningType).equals(TransactionRekeningType.DEPOSIT)) {
            BigDecimal transaksiPlusAdmin = jumlahTransaksi;
            if (saldoSaatIni.compareTo(transaksiPlusAdmin) < 0) {
                throw new Exception("Saldo anda tidak cukup melakukan aktivitas");
            }
            saldoAkhir = saldoSaatIni.subtract(transaksiPlusAdmin);
        } else if (TransactionRekeningType.fromString(transactionRekeningType).equals(TransactionRekeningType.DEPOSIT)) {
            saldoAkhir = saldoSaatIni.add(jumlahTransaksi);
        } else {
            throw new Exception("Tipe transaksi pada rekening tidak dikenal.");
        }

        return saldoAkhir;
    }

    public BigDecimal updateValueSaldoEwallet(Ewallet ewallet, String transactionEwalletType, BigDecimal jumlahTransaksi, BigDecimal saldoSaatIni) throws Exception {
        // Validasi input parameter
        if (transactionEwalletType == null) {
            throw new Exception("Gagal melakukan aktivitas, tipe transaksi tidak boleh null.");
        }

        BigDecimal saldoAkhir;
        if (TransactionEwalletType.fromString(transactionEwalletType).equals(TransactionEwalletType.TRANSFER) || TransactionEwalletType.fromString(transactionEwalletType).equals(TransactionEwalletType.PAYMENT)) {
            saldoAkhir = saldoSaatIni.subtract(jumlahTransaksi);
        } else if (TransactionEwalletType.fromString(transactionEwalletType).equals(TransactionEwalletType.TOPUP)) {
            TransaksiDto transaksiDto = new TransaksiDto();
            BigDecimal admin = ewallet.getBiayaAdmin().add(jumlahTransaksi);
            transaksiDto.setDeskripsi("payment to ewallet " + ewallet.getJenisEwallet());
            transaksiDto.setTipeTransaksi("payment");
            transaksiDto.setJumlah(admin);
            Nasabah nasabah = ewallet.getNasabah();
            List<Rekening> rekeningList = rekeningRepository.findByNasabahId(nasabah.getId());
            if (rekeningList.size() < 1 || rekeningList == null) {
                throw new Exception("Rekening atas nasabah id " + nasabah.getId() + "tidak ditemukan");
            }
            Rekening rekening = rekeningList.get(0);

            topUpEwallet(rekening.getId(), transaksiDto);
            saldoAkhir = saldoSaatIni.add(jumlahTransaksi);
        } else {
            throw new Exception("Tipe transaksi pada ewallet tidak dikenal.");
        }
        return saldoAkhir;
    }


    @Override
    public List<Transaksi> getTransaksiByNasabahId(Long nasabahId) {
        List<Rekening> rekeningList = rekeningRepository.findByNasabahId(nasabahId);
        List<Ewallet> ewalletList = ewalletRepository.findByNasabahId(nasabahId);

        List<Long> rekeningIds = new ArrayList<>();
        if (rekeningList != null || rekeningList.size() > 0) {
            rekeningIds = rekeningList.stream().map(Rekening::getId).collect(Collectors.toList());
        }
        List<Long> ewalletIds = new ArrayList<>();
        if (ewalletList != null || ewalletIds.size() > 0) {
            ewalletIds = ewalletList.stream().map(Ewallet::getId).collect(Collectors.toList());
        }

        if (rekeningIds.size() > 0 && ewalletIds.size() < 1) {
            return transaksiRepository.findByRekeningIdIn(rekeningIds);
        } else if (rekeningIds.size() < 1 && ewalletIds.size() > 0) {
            return transaksiRepository.findByEwalletIdIn(ewalletIds);
        } else if (rekeningIds.size() > 0 && ewalletIds.size() > 0) {
            return transaksiRepository.findByRekeningIdInOrEwalletIdIn(rekeningIds, ewalletIds);
        } else {
            return null;
        }
    }
}
