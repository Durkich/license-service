package com.example.license.repository;

import com.example.license.model.LicenseEventEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LicenseEventRepository extends CrudRepository<LicenseEventEntity, Long> {
    List<LicenseEventEntity> findBySentFalse();
}