package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.Operator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CHARGE_POLICY_PARAMETER_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "CHARGE_POLICY_PARAMETER_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class ChargePolicyParameterTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long chargePolicyParameterId;

    private String chargeDefinitionCode;

    @Enumerated(EnumType.STRING)
    private Operator operator;

    private Double value;
}