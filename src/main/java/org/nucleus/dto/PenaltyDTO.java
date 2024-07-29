package org.nucleus.dto;

public class PenaltyDTO {
    private Long penaltyId;
    private String description;
    private Double penaltyAmount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public Long getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(Long penaltyId) {
        this.penaltyId = penaltyId;
    }

    @Override
    public String toString() {
        return "PenaltyDTO{" +
                "penaltyId=" + penaltyId +
                ", description='" + description + '\'' +
                ", penaltyAmount=" + penaltyAmount +
                '}';
    }
}
