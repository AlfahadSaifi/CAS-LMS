package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CHARGE_DEFINITION_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "CHARGE_DEFINITION_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class ChargeDefinitionTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long chargeDefiniteId;
    private String chargeDefinitionCode;
    private String chargeName;
    private String description;
    private Double maximumAmount;
    private Double minimumAmount;
    @Embedded
    private TempMetaData metaData;
}