package org.nucleus.service;

import org.apache.logging.log4j.LogManager;
import org.nucleus.dao.LoanApplicationDAO;
import org.nucleus.dao.RepayScheduleDAO;
import org.nucleus.dto.EmiParameterDTO;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.entity.permanent.RepaySchedule;
import org.nucleus.entity.permanent.RepaymentPolicy;
import org.nucleus.entity.temporary.LoanApplicationTemp;
import org.nucleus.utility.EmiUtility;
import org.nucleus.utility.enums.RecoveryType;
import org.nucleus.utility.enums.RepaymentFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class RepayScheduleServiceImpl implements RepayScheduleService {


    RepayScheduleDAO repayScheduleDAO;

    EmiUtility emiUtility;

    LoanApplicationService loanApplicationService;

    LoanApplicationDAO loanApplicationDAO;
    LoanProductService loanProductService;

    RepaymentPolicyService repaymentPolicyService;


    @Autowired
    public RepayScheduleServiceImpl(RepaymentPolicyService repaymentPolicyService, LoanApplicationDAO loanApplicationDAO,LoanApplicationService loanApplicationService,EmiUtility emiUtility,RepayScheduleDAO repayScheduleDAO, LoanProductService loanProductService){
        this.loanApplicationDAO=loanApplicationDAO;
        this.loanApplicationService=loanApplicationService;
        this.repayScheduleDAO=repayScheduleDAO;
        this.emiUtility=emiUtility;
        this.loanProductService=loanProductService;
        this.repaymentPolicyService = repaymentPolicyService;
    }


    @Override
    public List<RepayScheduleDTO> fetchRepaySchedule(String loanApplicationId) {
        LoanApplication loanApplication=loanApplicationService.getLoanIdAgainstApplication(loanApplicationId);
        if(loanApplication==null){
            return null;
        }

        return repayScheduleDAO.fetchRepaySchedule(loanApplication.getLoanId());
    }

    @Override
    public EmiParameterDTO getEmiParams(Long loanId) {
        EmiParameterDTO emiParameters= new EmiParameterDTO();


        LoanApplicationTemp loanApplication=loanApplicationService.getLoanApplicationAgainstLoanId(loanId);
        if (loanApplication != null) {

            String tenureIn=loanApplication.getTenureIn().toUpperCase();

            switch(tenureIn){
                case "MONTHS":
                    emiParameters.setTenure(Double.valueOf(loanApplication.getTenure())/12);
                    break;
                case "WEEKS":
                    emiParameters.setTenure(Double.valueOf(loanApplication.getTenure())/52);
                    break;
                case "YEARS":
                    emiParameters.setTenure(Double.valueOf(loanApplication.getTenure()));
                    break;
                default:
                    LogManager.getLogger(RepayScheduleService.class).error("Wrong value selected for tenure!");
            }
            emiParameters.setLoanAmount(loanApplication.getLoanAmount());

        }

        LoanProductDTO loanProduct=loanProductService.getLoanProductByID(loanApplication.getLoanProduct().getProductId());
        if (loanProduct != null) {
            emiParameters.setRate(loanProduct.getRateOfInterest());
        }

        RepaymentPolicy repaymentPolicy=repaymentPolicyService.getRepayPolicyByCode(loanProduct.getRepaymentPolicyCode());

        if (repaymentPolicy != null) {
            RecoveryType recoveryType = repaymentPolicy.getRecoveryType();

            switch (recoveryType) {
                case INSTALLMENT:
                    emiParameters.setFixedOrFloat("FIXED");
                    break;
                case NONINSTALLMENT:
                    emiParameters.setFixedOrFloat("FLOATING");
                    break;

                default:
                    LogManager.getLogger(RepayScheduleService.class).error("Wrong value selected for recovery type!");
            }


            RepaymentFrequency repaymentFrequency = repaymentPolicy.getRepaymentFrequency();

            switch (repaymentFrequency) {
                case QUARTERLY:
                    emiParameters.setRepaymentFrequency(4);
                    break;
                case HALF_YEARLY:
                    emiParameters.setRepaymentFrequency(2);
                    break;
                case YEARLY:
                    emiParameters.setRepaymentFrequency(1);
                    break;
                case MONTHLY:
                    emiParameters.setRepaymentFrequency(12);
                    break;
                default:
                    LogManager.getLogger(RepayScheduleService.class).error("Wrong value selected for repayment frequency !");
            }
        }

        return emiParameters;
    }

    @Override
    public List<RepaySchedule> generateRepayScheduleForApproval(Long loanId) {
        EmiParameterDTO emiParameterDTO=getEmiParams(loanId);
        List<RepaySchedule> repayScheduleList;
        if(emiParameterDTO.getFixedOrFloat().equals("FIXED")) {
            repayScheduleList=emiUtility.generateRepayScheduleForFixedRate(emiParameterDTO.getLoanAmount() ,emiParameterDTO.getTenure(), emiParameterDTO.getRepaymentFrequency(), emiParameterDTO.getRate());
        }
        else{
            repayScheduleList=emiUtility.generateRepayScheduleForFloatingRate(emiParameterDTO.getLoanAmount() ,emiParameterDTO.getTenure(), emiParameterDTO.getRepaymentFrequency(), emiParameterDTO.getRate());
        }

        return repayScheduleList;
    }

    @Override
    public Date getNextInstallmentDate(List<RepayScheduleDTO> repayScheduleList) {

        Date currentDate=new Date(System.currentTimeMillis());
        for(RepayScheduleDTO repaySchedule:repayScheduleList)

        {

            Date dueDate = repaySchedule.getDueDate();
            if (dueDate.after(currentDate)) {
                return dueDate;

            }

        }
        return null;

    }

    @Override
    public Double getPrincipalOutstanding(List<RepayScheduleDTO> repayScheduleList) {

        Date currentDate=new Date(System.currentTimeMillis());

        Double outstandingBalancePrincipal=0.0;
        for(RepayScheduleDTO repaySchedule:repayScheduleList)

        {

            Date dueDate = repaySchedule.getDueDate();
            if (dueDate.before(currentDate)) {

                outstandingBalancePrincipal=repaySchedule.getOutstandingBalancePrincipal();

            }

        }
        return outstandingBalancePrincipal;

    }

    @Override
    public Integer getInstallmentsToBePaid(List<RepayScheduleDTO> repayScheduleList) {

        Date currentDate=new Date(System.currentTimeMillis());

        Integer installmentsToBePaid=0;
        for(RepayScheduleDTO repaySchedule:repayScheduleList)

        {

            Date dueDate = repaySchedule.getDueDate();
            if (dueDate.before(currentDate)) {

                installmentsToBePaid=repaySchedule.getInstallmentNumber();

            }

        }
        return installmentsToBePaid;

    }

    @Override
    public Double getOpeningBalance(List<RepayScheduleDTO> repayScheduleList,Integer installmentsPaid) {
        Date currentDate=new Date(System.currentTimeMillis());
        Double openingBalance=0.0;
        if(repayScheduleList!=null)
            openingBalance=repayScheduleList.get(installmentsPaid).getOpeningBalance();
        return openingBalance;
    }
}
