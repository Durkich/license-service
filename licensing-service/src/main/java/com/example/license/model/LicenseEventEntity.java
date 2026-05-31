package com.example.license.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Data
public class LicenseEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private Integer licenseId;
    private String softwareName;
    private String softwareVersion;
    private Boolean isLicensed;
    private LocalDateTime createdAt;
    private Boolean sent = false;
}