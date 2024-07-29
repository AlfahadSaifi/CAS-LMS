package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CHARGE_DEFINITION_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "CHARGE_DEFINITION_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class ChargeDefinition {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Long chargeDefiniteId;
    private String chargeDefinitionCode;
    private String chargeName;
    private String description;
    private Double maximumAmount;
    private Double minimumAmount;
    @Embedded
    private MetaData metaData;
}