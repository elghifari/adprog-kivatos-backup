package com.service.kivatos.repository;

import com.service.kivatos.models.Nasabah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NasabahRepository extends JpaRepository<Nasabah, Long> {
}
