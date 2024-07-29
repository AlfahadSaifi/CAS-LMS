package org.nucleus.dto;

import lombok.Data;

@Data
public class RescheduleResponseDTO {
    private String productCode;
    private String effectiveDate;
    private Integer currentDueDate;
    private Double currentInstallment;
    private Integer currentTenure;
    private String frequency;
    private Double rate;
    private String tenureIn;
}
