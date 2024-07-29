package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.TempMetaData;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ELIGIBILITY_POLICY_PARAMETER_DEFINITION_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "ELIGIBILITY_POLICY_PARAMETER_DEFINITION_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class EligibilityPolicyParameterDefinitionTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long eligibilityParameterId;
    @Column(unique = true)
    private String eligibilityParameterCode;
    private String eligibilityParameterName;
    private String eligibilityParameterDescription;
    @Embedded
    private TempMetaData metaData;
}