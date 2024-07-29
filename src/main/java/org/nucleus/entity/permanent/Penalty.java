package org.nucleus.entity.permanent;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PENALTY_TBL_BATCH_6")
public class Penalty {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long penaltyId;
    private String description;
    private Double penaltyAmount;
}
