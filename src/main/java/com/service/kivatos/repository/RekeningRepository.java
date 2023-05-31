package com.service.kivatos.repository;

import com.service.kivatos.models.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RekeningRepository extends JpaRepository<Rekening, Long> {
    List<Rekening> findByNasabahId(Long id);
}
