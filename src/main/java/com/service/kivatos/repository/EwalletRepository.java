package com.service.kivatos.repository;

import com.service.kivatos.models.Ewallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EwalletRepository extends JpaRepository<Ewallet, Long> {
    List<Ewallet> findByNasabahId(Long id);
}
