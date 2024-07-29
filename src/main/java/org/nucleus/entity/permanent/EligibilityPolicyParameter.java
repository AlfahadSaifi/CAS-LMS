package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.utility.enums.Operator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ELIGIBILITY_POLICY_PARAMETER_TBL_BATCH_6")
@TableGenerator(name = "ID_TABLE_GEN_BATCH_6", pkColumnValue = "ELIGIBILITY_POLICY_PARAMETER_TBL_BATCH_6", initialValue = 100000, allocationSize = 1)
public class EligibilityPolicyParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_TABLE_GEN_BATCH_6")
    private Long eligibilityPolicyParameterId;

    private String parameter;

    @Enumerated(EnumType.STRING)
    private Operator operator;

    private Integer value;

    @Embedded
    private MetaData metaData;
}
