package org.nucleus.entity.permanent;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ELIGIBILITY_POLICY_TBL_BATCH_6")
public class EligibilityPolicy {
    @Id
    private Long eligibilityPolicyId;
    private String eligibilityPolicyCode;
    private String eligibilityPolicyName;
    private String eligibilityCriteria;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "eligibilityPolicyId")
    private List<EligibilityPolicyParameter> eligibilityParameterMappingList;
    @Embedded
    private MetaData metaData;
}