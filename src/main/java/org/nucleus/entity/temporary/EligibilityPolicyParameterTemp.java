package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.Operator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ELIGIBILITY_POLICY_PARAMETER_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "ELIGIBILITY_POLICY_PARAMETER_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class EligibilityPolicyParameterTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long eligibilityPolicyParameterId;

    private String parameter;

    @Enumerated(EnumType.STRING)
    private Operator operator;

    private Integer value;
//
//    @ManyToOne
//    @JoinColumn(name = "eligibilityPolicyId")
//    private EligibilityPolicyTemp eligibilityPolicy;
//    @OneToOne
//    private EligibilityPolicyParameterDefinitionTemp definition;
    @Embedded
    private TempMetaData metaData;

}
