package org.nucleus.utility.dtomapper;

import org.nucleus.entity.permanent.*;
import org.nucleus.service.ChargePolicyService;
import org.nucleus.service.EligibilityPolicyCheckerService;
import org.nucleus.service.RepaymentPolicyService;
import org.nucleus.utility.temptoperm.EligibilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.entity.temporary.LoanProductTemp;
import org.nucleus.service.ProductTypeService;

@Component
public class LoanProductDTOMapper {

    private ProductTypeService productTypeService;
    private ProductTypeDTOMapper productTypeDTOMapper;
    private RepaymentPolicyService repaymentPolicyService;
    private ChargePolicyService chargePolicyService;
    private ChargePolicyDtoMapper chargePolicyDtoMapper;
    private EligibilityPolicyCheckerService eligibilityPolicyCheckerService;
    private EligibilityPolicyTempDTOMapper eligibilityPolicyTempDTOMapper;

    @Autowired
    public LoanProductDTOMapper(ProductTypeService productTypeService, ProductTypeDTOMapper productTypeDTOMapper, RepaymentPolicyService repaymentPolicyService, ChargePolicyService chargePolicyService, ChargePolicyDtoMapper chargePolicyDtoMapper, EligibilityPolicyCheckerService eligibilityPolicyCheckerService, EligibilityPolicyTempDTOMapper eligibilityPolicyTempDTOMapper) {
        this.productTypeService = productTypeService;
        this.productTypeDTOMapper = productTypeDTOMapper;
        this.repaymentPolicyService = repaymentPolicyService;
        this.chargePolicyService = chargePolicyService;
        this.chargePolicyDtoMapper = chargePolicyDtoMapper;
        this.eligibilityPolicyCheckerService = eligibilityPolicyCheckerService;
        this.eligibilityPolicyTempDTOMapper = eligibilityPolicyTempDTOMapper;
    }

    public LoanProductTemp dtoToLoanProductTemp(LoanProductDTO loanProductDTO) {
        LoanProductTemp loanProductTemp = new LoanProductTemp();
        if (loanProductDTO != null) {
            loanProductTemp.setProductId(loanProductDTO.getProductId());
            loanProductTemp.setProductCode(loanProductDTO.getProductCode());
            loanProductTemp.setProductDescription(loanProductDTO.getProductDescription());
            loanProductTemp.setProductName(loanProductDTO.getProductName());
            loanProductTemp.setRateOfInterest(loanProductDTO.getRateOfInterest());
            loanProductTemp.setSecurityType(loanProductDTO.getSecurityType());
            loanProductTemp.setMetaData(loanProductDTO.getMetaData());
            String productTypeCode = loanProductDTO.getProductTypeCode();
            if(productTypeCode != null && !productTypeCode.isEmpty())
                loanProductTemp.setProductType(
                        productTypeDTOMapper.dtoToMaster(productTypeService.getProductTypeByCode(productTypeCode))
                );

            String repaymentPolicyCode = loanProductDTO.getRepaymentPolicyCode();
            if(repaymentPolicyCode != null && !repaymentPolicyCode.isEmpty())
                loanProductTemp.setRepaymentPolicy(
                        RepaymentPolicyDtoMapper.dtoToMasterConvertor(repaymentPolicyService.getRepaymentPolicyById(repaymentPolicyCode))
                );

            String chargePolicyCode = loanProductDTO.getChargePolicyCode();
            if(chargePolicyCode != null && !chargePolicyCode.isEmpty())
                loanProductTemp.setChargePolicy(
                        chargePolicyDtoMapper.dtoToEntityConverter(chargePolicyService.getChargePolicy(chargePolicyCode))
                );

            String eligibilityPolicyCode = loanProductDTO.getEligibilityPolicyCode();
            if(eligibilityPolicyCode != null && !eligibilityPolicyCode.isEmpty())
                loanProductTemp.setEligibilityPolicy(
                        EligibilityMapper.convertor(eligibilityPolicyTempDTOMapper.mapPolicy(eligibilityPolicyCheckerService.getEligibilityPolicyByCode(eligibilityPolicyCode)))
                );
            return loanProductTemp;
        }
        return null;
    }

    public LoanProductDTO loanProductTempToDto(LoanProductTemp loanProductTemp) {
        LoanProductDTO loanProductDTO = new LoanProductDTO();
        if (loanProductTemp != null) {
            loanProductDTO.setProductId(loanProductTemp.getProductId());
            loanProductDTO.setProductCode(loanProductTemp.getProductCode());
            loanProductDTO.setProductDescription(loanProductTemp.getProductDescription());
            loanProductDTO.setProductName(loanProductTemp.getProductName());
            loanProductDTO.setRateOfInterest(loanProductTemp.getRateOfInterest());
            loanProductDTO.setSecurityType(loanProductTemp.getSecurityType());
            loanProductDTO.setMetaData(loanProductTemp.getMetaData());

            ProductType productType = loanProductTemp.getProductType();
            if(productType != null)
                loanProductDTO.setProductTypeCode(productType.getProductTypeCode());

            RepaymentPolicy repaymentPolicy = loanProductTemp.getRepaymentPolicy();
            if(repaymentPolicy != null)
                loanProductDTO.setRepaymentPolicyCode(repaymentPolicy.getRepaymentPolicyCode());

            ChargePolicy chargePolicy = loanProductTemp.getChargePolicy();
            if(chargePolicy != null)
                loanProductDTO.setChargePolicyCode(chargePolicy.getPolicyCode());

            EligibilityPolicy eligibilityPolicy = loanProductTemp.getEligibilityPolicy();
            if(eligibilityPolicy != null)
                loanProductDTO.setEligibilityPolicyCode(eligibilityPolicy.getEligibilityPolicyCode());

            return loanProductDTO;
        }
        return null;
    }

    public LoanProduct dtoToLoanProduct(LoanProductDTO loanProductDTO) {
        LoanProduct loanProduct = new LoanProduct();
        if (loanProductDTO != null) {
            loanProduct.setProductId(loanProductDTO.getProductId());
            loanProduct.setProductCode(loanProductDTO.getProductCode());
            loanProduct.setProductDescription(loanProductDTO.getProductDescription());
            loanProduct.setProductName(loanProductDTO.getProductName());
            loanProduct.setRateOfInterest(loanProductDTO.getRateOfInterest());
            loanProduct.setSecurityType(loanProductDTO.getSecurityType());
            loanProduct.setMetaData(MetaDataMapper.convertToMetaData(loanProductDTO.getMetaData()));

            String productTypeCode = loanProductDTO.getProductTypeCode();
            if(productTypeCode != null && !productTypeCode.isEmpty())
                loanProduct.setProductType(
                        productTypeDTOMapper.dtoToMaster(productTypeService.getProductTypeByCode(productTypeCode))
                );

            String repaymentPolicyCode = loanProductDTO.getRepaymentPolicyCode();
            if(repaymentPolicyCode != null && !repaymentPolicyCode.isEmpty())
                loanProduct.setRepaymentPolicy(
                        RepaymentPolicyDtoMapper.dtoToMasterConvertor(repaymentPolicyService.getRepaymentPolicyById(repaymentPolicyCode))
                );

            String chargePolicyCode = loanProductDTO.getChargePolicyCode();
            if(chargePolicyCode != null && !chargePolicyCode.isEmpty())
                loanProduct.setChargePolicy(
                        chargePolicyDtoMapper.dtoToEntityConverter(chargePolicyService.getChargePolicy(chargePolicyCode))
                );

            String eligibilityPolicyCode = loanProductDTO.getEligibilityPolicyCode();
            if(eligibilityPolicyCode != null && !eligibilityPolicyCode.isEmpty())
                loanProduct.setEligibilityPolicy(
                        EligibilityMapper.convertor(eligibilityPolicyTempDTOMapper.mapPolicy(eligibilityPolicyCheckerService.getEligibilityPolicyByCode(eligibilityPolicyCode)))
                );
            return loanProduct;
        }
        return null;
    }


    public LoanProductDTO loanProductToDto(LoanProduct loanProduct) {
        LoanProductDTO loanProductDTO = new LoanProductDTO();
        if (loanProduct != null) {
            loanProductDTO.setProductId(loanProduct.getProductId());
            loanProductDTO.setProductCode(loanProduct.getProductCode());
            loanProductDTO.setProductDescription(loanProduct.getProductDescription());
            loanProductDTO.setProductName(loanProduct.getProductName());
            loanProductDTO.setRateOfInterest(loanProduct.getRateOfInterest());
            loanProductDTO.setSecurityType(loanProduct.getSecurityType());
            loanProductDTO.setMetaData(MetaDataMapper.convertToTempMetaData(loanProduct.getMetaData()));

            ProductType productType = loanProduct.getProductType();
            if(productType != null)
                loanProductDTO.setProductTypeCode(productType.getProductTypeCode());

            RepaymentPolicy repaymentPolicy = loanProduct.getRepaymentPolicy();
            if(repaymentPolicy != null)
                loanProductDTO.setRepaymentPolicyCode(repaymentPolicy.getRepaymentPolicyCode());

            ChargePolicy chargePolicy = loanProduct.getChargePolicy();
            if(chargePolicy != null)
                loanProductDTO.setChargePolicyCode(chargePolicy.getPolicyCode());

            EligibilityPolicy eligibilityPolicy = loanProduct.getEligibilityPolicy();
            if(eligibilityPolicy != null)
                loanProductDTO.setEligibilityPolicyCode(eligibilityPolicy.getEligibilityPolicyCode());

            return loanProductDTO;
        }
        return null;
    }



}
