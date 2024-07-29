package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CHARGE_POLICY_TBL_BATCH_6")
public class ChargePolicy {
    @Id
    private String policyCode;
    private String policyName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_id")
    private List<ChargePolicyParameter> chargePolicyParameterList;

    @Embedded
    private MetaData metaData;
}
