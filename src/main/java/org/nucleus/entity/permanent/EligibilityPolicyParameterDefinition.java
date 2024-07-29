package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ELIGIBILITY_POLICY_PARAMETER_DEFINITION_TBL_BATCH_6")
public class EligibilityPolicyParameterDefinition {
    @Id
    private Long eligibilityParameterId;

    private String eligibilityParameterCode;
    private String eligibilityParameterName;
    private String eligibilityParameterDescription;
    @Embedded
    private MetaData metaData;
}