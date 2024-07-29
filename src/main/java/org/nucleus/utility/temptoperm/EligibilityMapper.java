package org.nucleus.utility.temptoperm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.EligibilityPolicy;
import org.nucleus.entity.permanent.EligibilityPolicyParameter;
import org.nucleus.entity.temporary.EligibilityPolicyParameterTemp;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.service.EligibilityPolicyMakerServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class EligibilityMapper {
    private static final Logger log = LogManager.getLogger(EligibilityMapper.class);
    private EligibilityMapper(){}
    public static EligibilityPolicy convertor(EligibilityPolicyTemp eligibilityPoliciesTemp)
    {
        EligibilityPolicy eligibilityPoliciesMst=new EligibilityPolicy();

        eligibilityPoliciesMst.setEligibilityPolicyId(eligibilityPoliciesTemp.getEligibilityPolicyId());
        eligibilityPoliciesMst.setEligibilityCriteria(eligibilityPoliciesTemp.getEligibilityCriteria());
        eligibilityPoliciesMst.setEligibilityPolicyCode(eligibilityPoliciesTemp.getEligibilityPolicyCode());
        eligibilityPoliciesMst.setEligibilityPolicyName(eligibilityPoliciesTemp.getEligibilityPolicyName());
        eligibilityPoliciesMst.setMetaData(tempToMetaData(eligibilityPoliciesTemp.getMetaData()));


        List<EligibilityPolicyParameter> parameterMsts=new ArrayList<>();
        for (EligibilityPolicyParameterTemp element : eligibilityPoliciesTemp.getEligibilityParameterMappingList()) {
            parameterMsts.add(parameterConverter(element));
        }
        eligibilityPoliciesMst.setEligibilityParameterMappingList(parameterMsts);
        log.info(eligibilityPoliciesMst);
        return eligibilityPoliciesMst;
    }
    public static EligibilityPolicyTemp convertorTemp(EligibilityPolicy eligibilityPolicy)
    {
        EligibilityPolicyTemp eligibilityPolicyTemp=new EligibilityPolicyTemp();

        eligibilityPolicyTemp.setEligibilityPolicyId(eligibilityPolicy.getEligibilityPolicyId());
        eligibilityPolicyTemp.setEligibilityCriteria(eligibilityPolicy.getEligibilityCriteria());
        eligibilityPolicyTemp.setEligibilityPolicyCode(eligibilityPolicy.getEligibilityPolicyCode());
        eligibilityPolicyTemp.setMetaData(metaDataToTemp(eligibilityPolicy.getMetaData()));
        eligibilityPolicyTemp.setEligibilityPolicyName(eligibilityPolicy.getEligibilityPolicyName());

        List<EligibilityPolicyParameterTemp> parameter=new ArrayList<>();
        for (EligibilityPolicyParameter element : eligibilityPolicy.getEligibilityParameterMappingList()) {
            parameter.add(parameterConverterTemp(element));
        }
        log.info("inside EligibilityPolicyTemp"+parameter);
        eligibilityPolicyTemp.setEligibilityParameterMappingList(parameter);
        return eligibilityPolicyTemp;
    }
    public static EligibilityPolicyParameterTemp parameterConverterTemp(EligibilityPolicyParameter policyEligibilityParameter)
    {
        EligibilityPolicyParameterTemp policyEligibilityParameterTemp=new EligibilityPolicyParameterTemp();
        policyEligibilityParameterTemp.setParameter(policyEligibilityParameter.getParameter());
        policyEligibilityParameterTemp.setEligibilityPolicyParameterId(policyEligibilityParameter.getEligibilityPolicyParameterId());
        policyEligibilityParameterTemp.setOperator(policyEligibilityParameter.getOperator());
        policyEligibilityParameterTemp.setValue(policyEligibilityParameter.getValue());
        return policyEligibilityParameterTemp;
    }

    public static  EligibilityPolicyParameter parameterConverter(EligibilityPolicyParameterTemp policyEligibilityParameterTemp)
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
        log.info(metaData);
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
