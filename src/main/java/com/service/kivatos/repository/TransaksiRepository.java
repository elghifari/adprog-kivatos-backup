package com.service.kivatos.repository;

import com.service.kivatos.models.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    List<Transaksi> findByRekeningIdInOrEwalletIdIn(List<Long> rekeningIds, List<Long> ewalletIds);
    List<Transaksi> findByRekeningIdIn(List<Long> rekeningIds);
    List<Transaksi> findByEwalletIdIn(List<Long> ewalletIds);
}
