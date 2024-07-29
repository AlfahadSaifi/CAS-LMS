package org.nucleus.service;

import org.nucleus.dao.LoanApplicationDAO;
import org.nucleus.dao.LoanProductDAO;
import org.nucleus.dao.RepayScheduleDAO;
import org.nucleus.dao.RepaymentPolicyDao;
import org.nucleus.dto.EmiParameterDTO;
import org.nucleus.dto.LoanProductDTO;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.dto.RescheduleResponseDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.entity.permanent.RepaySchedule;
import org.nucleus.entity.permanent.RepaymentPolicy;
import org.nucleus.utility.EmiUtility;
import org.nucleus.utility.dtomapper.RepayScheduleMapper;
import org.nucleus.utility.enums.RecoveryType;
import org.nucleus.utility.enums.RepaymentFrequency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class RescheduleServiceImpl implements RescheduleService{
    private final LoanApplicationDAO loanApplicationDAO;
    private final RepayScheduleDAO repayScheduleDAO;
    private final LoanProductDAO loanProductDAO;
    private final RepaymentPolicyDao repaymentPolicyDao;
    private List<RepayScheduleDTO> repayScheduleDTOS = null;
    private RepayScheduleDTO toRescheduleFrom = null;
    private LoanApplication loanApplication = null;
    private String lastStatus = "";
    private final String INTERNAL_SERVER_ERROR = "Internal Server Error ";
    public RescheduleServiceImpl(LoanApplicationDAO loanApplicationDAO, RepayScheduleDAO repayScheduleDAO, LoanProductDAO loanProductDAO, RepaymentPolicyDao repaymentPolicyDao){
        this.loanApplicationDAO = loanApplicationDAO;
        this.repayScheduleDAO = repayScheduleDAO;
        this.loanProductDAO = loanProductDAO;
        this.repaymentPolicyDao = repaymentPolicyDao;
    }
    @Transactional
    public String getCustomerNameByLoanAccountNumber(String loanAccountNumber){
        if(loanAccountNumber == null) {
            lastStatus = "loanAccountNumber can't be null";
            throw new NullPointerException(lastStatus);
        }
        String customerName = null;
        try {
            customerName = loanApplicationDAO.getApplicationByAccountNumber(loanAccountNumber).getCustomer().getPersonInfoTemp().getFullName();
        } catch (NullPointerException e){
            lastStatus = INTERNAL_SERVER_ERROR;
            throw e;
        } catch (Exception e){
            lastStatus = INTERNAL_SERVER_ERROR + e.getMessage();
            throw e;
        }
        return customerName;
    }
    @Transactional
    public RescheduleResponseDTO getRescheduleResponseDTOByLoanAccountNumber(String loanAccountNumber) {
        if(loanAccountNumber == null) {
            lastStatus = "loanAccountNumber can't be null";
            throw new NullPointerException(lastStatus);
        }
        RescheduleResponseDTO rescheduleResponseDTO = null;
        repayScheduleDTOS = null;
        toRescheduleFrom = null;
        try {
            rescheduleResponseDTO = new RescheduleResponseDTO();
            loanApplication = loanApplicationDAO.getApplicationByAccountNumber(loanAccountNumber);
            repayScheduleDTOS = repayScheduleDAO.fetchRepaySchedule(loanApplication.getLoanId());
            LocalDate currentDate = LocalDate.now();
            for (RepayScheduleDTO repayScheduleDTO : repayScheduleDTOS) {
                if (repayScheduleDTO.getDueDate().toLocalDate().isAfter(currentDate)) {
                    toRescheduleFrom = repayScheduleDTO;
                    break;
                }
            }
            if (toRescheduleFrom == null) {
                lastStatus = "Cannot Reschedule the current repayment schedule";
                return null;
            }
            rescheduleResponseDTO.setProductCode(loanApplication.getLoanProduct().getProductCode());
            rescheduleResponseDTO.setEffectiveDate(toRescheduleFrom.getDueDate().toString());
            rescheduleResponseDTO.setCurrentDueDate(toRescheduleFrom.getDueDate().toLocalDate().getDayOfMonth());
            rescheduleResponseDTO.setCurrentInstallment(toRescheduleFrom.getInstallmentAmount());
            rescheduleResponseDTO.setCurrentTenure(loanApplication.getTenure());
            rescheduleResponseDTO.setTenureIn(loanApplication.getTenureIn());
            rescheduleResponseDTO.setFrequency(loanApplication.getLoanProduct().getRepaymentPolicy().getRepaymentFrequency().toString());
            rescheduleResponseDTO.setRate(toRescheduleFrom.getEffectiveInterestRate());
        } catch (NullPointerException e){
            lastStatus = INTERNAL_SERVER_ERROR;
            throw e;
        } catch (Exception e){
            lastStatus = INTERNAL_SERVER_ERROR + e.getMessage();
            throw e;
        }
        return rescheduleResponseDTO;
    }
    @Transactional
    public List<RepayScheduleDTO> rescheduleByDueDate(String loanAccountNumber, Integer dueDate){
        if(loanAccountNumber == null || dueDate == null) {
            lastStatus = "loanAccountNumber or dueDate can't be null";
            throw new NullPointerException(lastStatus);
        }
        try {
            for (RepayScheduleDTO repayScheduleDTO : repayScheduleDTOS)
                if (repayScheduleDTO.getInstallmentNumber() >= toRescheduleFrom.getInstallmentNumber())
                    repayScheduleDTO.setDueDate(Date.valueOf(repayScheduleDTO.getDueDate().toLocalDate().withDayOfMonth(dueDate)));
        } catch (NullPointerException e){
            lastStatus = INTERNAL_SERVER_ERROR;
            throw e;
        } catch (Exception e){
            lastStatus = INTERNAL_SERVER_ERROR + e.getMessage();
            throw e;
        }
        return repayScheduleDTOS;
    }
    @Transactional
    public List<RepayScheduleDTO> rescheduleByTenure(String loanAccountNumber, Integer tenure, String tenureIn){
        if(loanAccountNumber == null || tenure == null || tenureIn == null) {
            lastStatus = "loanAccountNumber, tenure or tenureIn can't be null";
            throw new NullPointerException(lastStatus);
        }
        EmiUtility emiUtility = new EmiUtility();
        try {
            List<RepayScheduleDTO> repayScheduleDTOList = new ArrayList<>();
            for (RepayScheduleDTO repayScheduleDTO : repayScheduleDTOS)
                if (repayScheduleDTO.getInstallmentNumber() < toRescheduleFrom.getInstallmentNumber())
                    repayScheduleDTOList.add(repayScheduleDTO);
            EmiParameterDTO emiParameterDTO = getEmiParamsForTenureChange(loanAccountNumber, tenure, tenureIn);
            List<RepaySchedule> newRepaySchedule;
            if(repayScheduleDTOList.isEmpty())
                newRepaySchedule = emiUtility.generateRepayScheduleForFixedRateForTenureChange(toRescheduleFrom.getInstallmentNumber(),
                        loanApplication.getLoanAmount()
                        ,emiParameterDTO.getTenure()
                        ,emiParameterDTO.getRepaymentFrequency()
                        ,emiParameterDTO.getRate());
            else
                newRepaySchedule = emiUtility.generateRepayScheduleForFixedRateForTenureChange(toRescheduleFrom.getInstallmentNumber(),
                        repayScheduleDTOList.get(repayScheduleDTOList.size()-1).getOutstandingBalancePrincipal()
                        ,emiParameterDTO.getTenure()
                        ,emiParameterDTO.getRepaymentFrequency()
                        ,emiParameterDTO.getRate());
            for(RepaySchedule repaySchedule : newRepaySchedule)
                repayScheduleDTOList.add(RepayScheduleMapper.convertToDTO(repaySchedule));
            repayScheduleDTOS = repayScheduleDTOList;
//            Period period = Period.between(repayScheduleDTOS.get(0).getDueDate().toLocalDate().minusMonths(1), repayScheduleDTOS.get(repayScheduleDTOS.size()-1).getDueDate().toLocalDate());
//            int updatedTenure = (period.getYears()*12) + period.getMonths();
//            loanApplication.setTenure(updatedTenure);
//            loanApplication.setTenureIn("Months");
        } catch (NullPointerException e){
            lastStatus = INTERNAL_SERVER_ERROR;
            throw e;
        } catch (Exception e){
            lastStatus = INTERNAL_SERVER_ERROR + e.getMessage();
            throw e;
        }
        return repayScheduleDTOS;
    }
    @Transactional
    public List<RepayScheduleDTO> rescheduleByInstallmentAmount(String loanAccountNumber, Double installmentAmount) {
        if(loanAccountNumber == null || installmentAmount == null) {
            lastStatus = "loanAccountNumber or installmentAmount can't be null";
            throw new NullPointerException(lastStatus);
        }
        EmiUtility emiUtility = new EmiUtility();
        try {
            List<RepayScheduleDTO> repayScheduleDTOList = new ArrayList<>();
            for (RepayScheduleDTO repayScheduleDTO : repayScheduleDTOS)
                if (repayScheduleDTO.getInstallmentNumber() < toRescheduleFrom.getInstallmentNumber())
                    repayScheduleDTOList.add(repayScheduleDTO);
            EmiParameterDTO emiParameterDTO = getEmiParamsForInstallmentChange(loanAccountNumber);
            List<RepaySchedule> newRepaySchedule;
            if(repayScheduleDTOList.isEmpty())
                newRepaySchedule = emiUtility.generateRepayScheduleForFixedRateByInstallmentAmount(toRescheduleFrom.getInstallmentNumber(),
                    loanApplication.getLoanAmount()
                    ,installmentAmount
                    ,emiParameterDTO.getRepaymentFrequency()
                    ,emiParameterDTO.getRate());
            else
                newRepaySchedule = emiUtility.generateRepayScheduleForFixedRateByInstallmentAmount(toRescheduleFrom.getInstallmentNumber(),
                        repayScheduleDTOList.get(repayScheduleDTOList.size()-1).getOutstandingBalancePrincipal()
                        ,installmentAmount
                        ,emiParameterDTO.getRepaymentFrequency()
                        ,emiParameterDTO.getRate());
            for(RepaySchedule repaySchedule : newRepaySchedule)
                repayScheduleDTOList.add(RepayScheduleMapper.convertToDTO(repaySchedule));
            repayScheduleDTOS = repayScheduleDTOList;
//            Period period = Period.between(repayScheduleDTOS.get(0).getDueDate().toLocalDate().minusMonths(1), repayScheduleDTOS.get(repayScheduleDTOS.size()-1).getDueDate().toLocalDate());
//            int updatedTenure = (period.getYears()*12) + period.getMonths();
//            loanApplication.setTenure(updatedTenure);
//            loanApplication.setTenureIn("Months");
        } catch (NullPointerException e){
            lastStatus = INTERNAL_SERVER_ERROR;
            throw e;
        } catch (Exception e){
            lastStatus = INTERNAL_SERVER_ERROR + e.getMessage();
            throw e;
        }
        return repayScheduleDTOS;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean saveUpdatedRepaySchedule(){
        List<RepaySchedule> repaySchedules = new ArrayList<>();
        for(RepayScheduleDTO repayScheduleDTO : repayScheduleDTOS)
            repaySchedules.add(RepayScheduleMapper.convertToEntity(repayScheduleDTO));
        Period period = Period.between(repayScheduleDTOS.get(0).getDueDate().toLocalDate().minusMonths(1), repayScheduleDTOS.get(repayScheduleDTOS.size()-1).getDueDate().toLocalDate());
        int updatedTenure = (period.getYears()*12) + period.getMonths();
        loanApplication.setTenure(updatedTenure);
        loanApplication.setTenureIn("Months");
        loanApplication.setRepaySchedules(repaySchedules);
        if(loanApplicationDAO.merge(loanApplication)) {
            lastStatus = "Repay Updated Successfully";
            return true;
        }
        lastStatus = "Repay Does Not Updated";
        return false;
    }
    public String getLastStatus(){
        return lastStatus;
    }

    public EmiParameterDTO getEmiParamsForTenureChange(String loanAccountNumber, Integer tenure, String tenureIn) {
        EmiParameterDTO emiParameters= new EmiParameterDTO();
        LoanApplication currentLoanApplication=loanApplicationDAO.getApplicationByAccountNumber(loanAccountNumber);
        if(currentLoanApplication == null)
            return null;
        tenureIn=tenureIn.toUpperCase();
        switch(tenureIn){
            case "MONTHS":
                emiParameters.setTenure(Double.valueOf(tenure)/12);
                break;
            case "WEEKS":
                emiParameters.setTenure(Double.valueOf(tenure)/52);
                break;
            case "YEARS":
                emiParameters.setTenure(Double.valueOf(tenure));
                break;
            default:
                throw new IllegalArgumentException("Wrong value selected for tenure!");
        }
        emiParameters.setLoanAmount(currentLoanApplication.getLoanAmount());
        LoanProductDTO loanProduct=loanProductDAO.getLoanProductByID(currentLoanApplication.getLoanProduct().getProductId());
        if(loanProduct == null)
            return null;
        emiParameters.setRate(loanProduct.getRateOfInterest());
        RepaymentPolicy repaymentPolicy=repaymentPolicyDao.getRepayPolicyByCode(loanProduct.getRepaymentPolicyCode());
        if(repaymentPolicy == null)
            return null;
        RecoveryType recoveryType = repaymentPolicy.getRecoveryType();
        switch (recoveryType) {
            case INSTALLMENT:
                emiParameters.setFixedOrFloat("FIXED");
                break;
            case NONINSTALLMENT:
                emiParameters.setFixedOrFloat("FLOATING");
                break;

            default:
                throw new IllegalArgumentException("Wrong value selected for recovery type!");
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
                throw new IllegalArgumentException("Wrong value selected for repayment frequency !");
        }
        return emiParameters;
    }
    public EmiParameterDTO getEmiParamsForInstallmentChange(String loanAccountNumber) {
        EmiParameterDTO emiParameters= new EmiParameterDTO();
        LoanApplication currentLoanApplication=loanApplicationDAO.getApplicationByAccountNumber(loanAccountNumber);
        if(currentLoanApplication == null)
            return null;
        String tenureIn=currentLoanApplication.getTenureIn().toUpperCase();
        switch(tenureIn){
            case "MONTHS":
                emiParameters.setTenure(Double.valueOf(currentLoanApplication.getTenure())/12);
                break;
            case "WEEKS":
                emiParameters.setTenure(Double.valueOf(currentLoanApplication.getTenure())/52);
                break;
            case "YEARS":
                emiParameters.setTenure(Double.valueOf(currentLoanApplication.getTenure()));
                break;
            default:
                throw new IllegalArgumentException("Wrong value selected for tenure!");
        }
        emiParameters.setLoanAmount(currentLoanApplication.getLoanAmount());
        LoanProductDTO loanProduct=loanProductDAO.getLoanProductByID(currentLoanApplication.getLoanProduct().getProductId());
        if(loanProduct == null)
            return null;
        emiParameters.setRate(loanProduct.getRateOfInterest());
        RepaymentPolicy repaymentPolicy=repaymentPolicyDao.getRepayPolicyByCode(loanProduct.getRepaymentPolicyCode());
        if(repaymentPolicy == null)
            return null;
        RecoveryType recoveryType = repaymentPolicy.getRecoveryType();
        switch (recoveryType) {
            case INSTALLMENT:
                emiParameters.setFixedOrFloat("FIXED");
                break;
            case NONINSTALLMENT:
                emiParameters.setFixedOrFloat("FLOATING");
                break;

            default:
                throw new IllegalArgumentException("Wrong value selected for recovery type!");
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
                throw new IllegalArgumentException("Wrong value selected for repayment frequency !");
        }
        return emiParameters;
    }
}
