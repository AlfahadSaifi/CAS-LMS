package org.nucleus.utility.dtomapper;

import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.EligibilityPolicy;
import org.nucleus.entity.permanent.EligibilityPolicyParameter;
import org.nucleus.entity.temporary.EligibilityPolicyParameterTemp;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;

import java.util.ArrayList;
import java.util.List;

public class EligibilityPolicyDTOMapper {
    private EligibilityPolicyDTOMapper(){}
    public static EligibilityPolicy convertor(EligibilityPolicyTemp eligibilityPoliciesTemp)
    {
        EligibilityPolicy eligibilityPoliciesMst=new EligibilityPolicy();

        eligibilityPoliciesMst.setEligibilityPolicyId(eligibilityPoliciesTemp.getEligibilityPolicyId());
        eligibilityPoliciesMst.setEligibilityCriteria(eligibilityPoliciesTemp.getEligibilityCriteria());
        eligibilityPoliciesMst.setEligibilityPolicyCode(eligibilityPoliciesTemp.getEligibilityPolicyCode());
        eligibilityPoliciesMst.setMetaData(tempToMetaData(eligibilityPoliciesTemp.getMetaData()));


        List<EligibilityPolicyParameter> parameterMsts=new ArrayList<>();
        for (EligibilityPolicyParameterTemp element : eligibilityPoliciesTemp.getEligibilityParameterMappingList()) {
            parameterMsts.add(parameterConverter(element));
        }
        eligibilityPoliciesMst.setEligibilityParameterMappingList(parameterMsts);
        return eligibilityPoliciesMst;
    }


    public static EligibilityPolicyParameter parameterConverter(EligibilityPolicyParameterTemp policyEligibilityParameterTemp)
    {
        EligibilityPolicyParameter policyEligibilityParameterMst=new EligibilityPolicyParameter();
        policyEligibilityParameterMst.setParameter(policyEligibilityParameterTemp.getParameter());
        policyEligibilityParameterMst.setEligibilityPolicyParameterId(policyEligibilityParameterTemp.getEligibilityPolicyParameterId());
        policyEligibilityParameterMst.setOperator(policyEligibilityParameterTemp.getOperator());
        policyEligibilityParameterMst.setValue(policyEligibilityParameterTemp.getValue());
        return policyEligibilityParameterMst;
    }

    public static MetaData tempToMetaData(TempMetaData tempMetaData){
        MetaData metaData=new MetaData();
        metaData.setRecordStatus(tempMetaData.getRecordStatus());
        metaData.setAuthorizedBy(tempMetaData.getAuthorizedBy());
        metaData.setAuthorizedDate(tempMetaData.getAuthorizedDate());
        metaData.setActiveInactiveFlag(tempMetaData.getActiveInactiveFlag());
        metaData.setCreatedBy(tempMetaData.getCreatedBy());
        metaData.setCreationDate(tempMetaData.getCreationDate());
        metaData.setModifiedBy(tempMetaData.getModifiedBy());
        metaData.setModifiedDate(tempMetaData.getModifiedDate());

        return metaData;
    }

    public static TempMetaData metaDataToTemp(MetaData metaData){
        TempMetaData tempMetaData = new TempMetaData();
        tempMetaData.setRecordStatus(metaData.getRecordStatus());
        tempMetaData.setAuthorizedBy(metaData.getAuthorizedBy());
        tempMetaData.setAuthorizedDate(metaData.getAuthorizedDate());
        tempMetaData.setActiveInactiveFlag(metaData.getActiveInactiveFlag());
        tempMetaData.setCreatedBy(metaData.getCreatedBy());
        tempMetaData.setCreationDate(metaData.getCreationDate());
        tempMetaData.setModifiedBy(metaData.getModifiedBy());
        tempMetaData.setModifiedDate(metaData.getModifiedDate());
        tempMetaData.setSaveFlag(false);

        return tempMetaData;
    }
}
