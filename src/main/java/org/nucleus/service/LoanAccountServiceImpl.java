package org.nucleus.service;
import org.nucleus.dao.LoanAccountDAO;
import org.nucleus.dto.ChargeDefinitionDTO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.dto.LoanAccountRequiredFieldDTO;
import org.nucleus.entity.permanent.ChargePolicyParameter;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.utility.dtomapper.LoanApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class LoanAccountServiceImpl implements LoanAccountService{
    private final LoanAccountDAO loanAccountDAO;
    private final RepayScheduleService repayScheduleService;
    private final LoanApplicationService loanApplicationService;
    private final LoanProductService loanProductService;
    private final ChargeDefinitionService chargeDefinitionService;
    @Autowired
    public LoanAccountServiceImpl(LoanAccountDAO loanAccountDAO,
                                  RepayScheduleService repayScheduleService,
                                  LoanApplicationService loanApplicationService, LoanProductService loanProductService, ChargeDefinitionService chargeDefinitionService) {
        this.loanAccountDAO = loanAccountDAO;
        this.repayScheduleService=repayScheduleService;
        this.loanApplicationService=loanApplicationService;
        this.loanProductService = loanProductService;
        this.chargeDefinitionService = chargeDefinitionService;
    }
    @Override
    public boolean insertLoanAccount(LoanAccountDTO loanAccountDTO) {
        return loanAccountDAO.insertLoanAccount(loanAccountDTO);
    }
    @Override
    public LoanAccountDTO getByLoanAccountId(Long loanAccountId) {
        return loanAccountDAO.getByLoanAccountId(loanAccountId);
    }
    @Override
    public LoanAccountDTO getByAccountNumber(String accountNumber) {
        LoanAccountDTO loanAccountDTO=loanAccountDAO.getByAccountNumber(accountNumber);
        return loanAccountDTO;
    }
    @Override
    public boolean updateLoanAccount(LoanAccountDTO loanAccount) {
        return loanAccountDAO.updateLoanAccount(loanAccount);
    }
    @Override
    public boolean delete(String accountNumber) {
        return loanAccountDAO.delete(accountNumber);
    }
    @Override
    public List<LoanAccountDTO> getAllLoanAccounts() {
        return loanAccountDAO.getAllLoanAccounts();
    }
    @Override
    public boolean isLoanAccountPresent(String accountNumber){
        List<LoanAccountDTO> loanAccounts=loanAccountDAO.getAllLoanAccounts();
        for(LoanAccountDTO loanAccountDTO:loanAccounts){
            if(loanAccountDTO.getLoanAccountNumber().equalsIgnoreCase(accountNumber)){
                return true;
            }
        }
        return false;
    }

    @Override
    public LoanAccountRequiredFieldDTO getAllFields(String accountNumber){
        LoanAccountRequiredFieldDTO requiredField=new LoanAccountRequiredFieldDTO();
        LoanApplication loanApplication=loanApplicationService.getApplicationByAccountNumber(accountNumber);
        if(loanApplication !=null) {
            List<RepayScheduleDTO> repayScheduleDTOS = repayScheduleService.fetchRepaySchedule(loanApplication.getLoanApplicationId());
            if(repayScheduleDTOS.size()>0){
                requiredField.setNumberOfInstallment(repayScheduleDTOS.size());
                requiredField.setNoOfInstallmentsUnbilled(repayScheduleDTOS.size());
                requiredField.setNoOfInstallmentsBilled(0);
                requiredField.setEmiAmt(repayScheduleDTOS.get(0).getInstallmentAmount());
            }
            requiredField.setSanctionedAmt(loanApplication.getLoanAmount());
            requiredField.setDisbursalDate(Date.valueOf(LocalDate.now()));
            requiredField.setRoi(loanApplication.getLoanProduct().getRateOfInterest());

            requiredField.setLoanSanctionDate(loanApplication.getMetaData().getAuthorizedDate());

            if(loanApplication.getLoanProduct() !=null && loanApplication.getLoanProduct().getChargePolicy() !=null ) {
                List<ChargePolicyParameter> chargePolicyParameterList = loanApplication.getLoanProduct().getChargePolicy().getChargePolicyParameterList();
                Double chargeAmount = 0.0;
                for (ChargePolicyParameter chargePolicyParameter : chargePolicyParameterList) {
                    ChargeDefinitionDTO chargeDefinitionDTO = chargeDefinitionService.getChargeDefinitionFromTheMasterTableByCode(chargePolicyParameter.getChargeDefinitionCode());
                    if ( chargeDefinitionDTO !=null && ("PreProcessingFees".equalsIgnoreCase(chargeDefinitionDTO.getChargeName()) || "verificationFees".equalsIgnoreCase(chargeDefinitionDTO.getChargeName())) ) {
                        chargeAmount += chargePolicyParameter.getValue();
                    }
                }
                System.out.println("Charge Amount "+chargeAmount);
                Double disbursedAmt = loanApplication.getLoanAmount() - chargeAmount;
                DecimalFormat df = new DecimalFormat("#.##");
                disbursedAmt = Double.parseDouble(df.format(disbursedAmt));
                requiredField.setDisbursedAmt(disbursedAmt);
            }
            requiredField.setCifNumber(loanApplication.getCustomer().getCifNumber());
            requiredField.setRepaymentFrequency(loanApplication.getLoanProduct().getRepaymentPolicy().getRepaymentFrequency());
            requiredField.setProduct(loanApplication.getLoanProduct().getProductId());
            requiredField.setProductType(loanApplication.getLoanProduct().getProductType().getProductTypeId());
            //LoanApplicationMapper loanApplicationMapper = new LoanApplicationMapper();
            requiredField.setLoanApplication(loanApplication);
        }
        return requiredField;
    }
    @Override
    public Long getRowCount() {
        return loanAccountDAO.getRowCount();
    }
    @Override
    public List<LoanAccountDTO> getLoanAccountsInBatch(int offset, int batchSize) {
        return loanAccountDAO.getLoanAccountsInBatch(offset,batchSize);
    }
}
