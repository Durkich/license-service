package com.example.license.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseEvent {
    private String eventType;
    private Integer licenseId;
    private String softwareName;
    private String softwareVersion;
    private Boolean isLicensed;
}