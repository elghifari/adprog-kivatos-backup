package com.service.kivatos.repository;

import com.service.kivatos.models.TransaksiDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaksiDetailRepository extends JpaRepository<TransaksiDetail, Long> {
}
