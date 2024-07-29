package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.ChargePolicyParameter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CHARGE_POLICY_TEMP_TBL_BATCH_6")
public class ChargePolicyTemp {
    @Id
    private String policyCode;
    private String policyName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_id")
    private List<ChargePolicyParameterTemp> chargePolicyParameterTempList;

    @Embedded
    private TempMetaData tempMetaData;
}
