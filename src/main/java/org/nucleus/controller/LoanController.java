package org.nucleus.controller;

import org.nucleus.dto.*;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.entity.permanent.LoanSummary;
import org.nucleus.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
public class LoanController {
    private final LoanAccountService loanAccountService;
    private final CustomerService customerService;
    private final RepayScheduleService repayScheduleService;
    private final LoanApplicationService loanApplicationService;
    private final ReceivablePayableService receivablePayableService;
    private final AllocationService allocationService;

    @Autowired
    public LoanController(LoanAccountService loanAccountService, CustomerService customerService, RepayScheduleService repayScheduleService, LoanApplicationService loanApplicationService, ReceivablePayableService receivablePayableService, AllocationService allocationService) {
        this.loanAccountService = loanAccountService;
        this.customerService = customerService;
        this.repayScheduleService = repayScheduleService;
        this.loanApplicationService = loanApplicationService;
        this.receivablePayableService = receivablePayableService;
        this.allocationService = allocationService;

    }

    @RequestMapping("loan-summary")
    public String home() {
        return "loan-summary";

    }

    @GetMapping("get-loan-summary")
    public String gettingLoanDetails(@RequestParam("loanAccountNumber") String loanAccountNumber, Model model) {

        LoanSummary loanSummaryEntity = new LoanSummary();

        LoanAccountDTO loanAccount = loanAccountService.getByAccountNumber(loanAccountNumber);
        if (loanAccount != null) {

            Long loanAccountId = loanAccount.getLoanAccountId();

            Double emiAmount = loanAccount.getOriginalEmiAmount();

            Double loanAmount = loanAccount.getFinalSanctionedAmount();

            String cifNumber = loanAccount.getCifNumber();

            loanSummaryEntity.setLoanAccountNumber(loanAccountNumber);

            loanSummaryEntity.setEmiAmount(emiAmount);

            loanSummaryEntity.setLoanAmount(loanAmount);

            List<AllocationDTO> allocations = allocationService.getAllocationByLoanAccountId(loanAccountId);

            System.out.println("Allocation is ");

            System.out.println(allocations);
            Double penaltyChargesPaid = allocationService.calculateTotalPenalty(allocations);

            //Double penaltyChargesPaid =500.0;

            loanSummaryEntity.setPenaltyChargesPaid(penaltyChargesPaid);

            List<ReceivablePayableDTO> receivableList = receivablePayableService.getReceivablePayableBYLoanAccId(loanAccountId);

            Double penaltyChargesRemaining = 0.0;
            if (receivableList != null)

                penaltyChargesRemaining = receivablePayableService.calculateUnpaidPenalty(receivableList);

            CustomerDTO customer = customerService.getByCIFNumber(cifNumber);
            if (customer != null) {

                Long contactNumber = customer.getContactNumber();

                loanSummaryEntity.setContactNumber(contactNumber);
                if (customer.getCustomerId() > 0) {

                    PersonInfoDTO personInfo = customer.getPersonInfoDTO();
                    if (personInfo != null) {

                        String fullName = personInfo.getFullName();

                        loanSummaryEntity.setFullName(fullName);

                    }

                }

            }

            LoanApplicationDTO loanApplication = loanApplicationService.readPermanentByLoanAccountNumber(loanAccountNumber);
            if (loanApplication != null) {

                String loanApplicationId = loanApplication.getLoanApplicationId();

                List<RepayScheduleDTO> repayScheduleList = repayScheduleService.fetchRepaySchedule(loanApplicationId);
                if (repayScheduleList != null) {

                    Integer totalInstallments = repayScheduleList.size();

                    loanSummaryEntity.setTotalInstallments(totalInstallments);

                    Date maturityDate = Date.valueOf(LocalDate.now());
                    for (RepayScheduleDTO repaySchedule : repayScheduleList) {

                        maturityDate = repaySchedule.getDueDate();

                    }

                    loanSummaryEntity.setMaturityDate(maturityDate);

                    Date nextInstallmentDate = repayScheduleService.getNextInstallmentDate(repayScheduleList);

                    loanSummaryEntity.setNextInstallmentDate(nextInstallmentDate);

                    Integer installmentsPaid = loanAccount.getNumberOfInstallmentBilled();

                    DecimalFormat df = new DecimalFormat("#.##");

                    Double outstandingBalancePrincipal = repayScheduleService.getOpeningBalance(repayScheduleList,installmentsPaid);

                    String formattedOutstandingBalancePrincipal = df.format(outstandingBalancePrincipal);

                    outstandingBalancePrincipal=Double.parseDouble(formattedOutstandingBalancePrincipal);

                    loanSummaryEntity.setOutstandingBalancePrincipal(outstandingBalancePrincipal);

                    Integer installmentsToBePaid = repayScheduleService.getInstallmentsToBePaid(repayScheduleList);


                    loanSummaryEntity.setInstallmentsPaid(installmentsPaid);
//                    Integer installmentsRemaining = Math.abs(installmentsToBePaid - installmentsPaid);
                    Integer installmentsRemaining = installmentsToBePaid - installmentsPaid;
                    if(installmentsRemaining<0)
                        installmentsRemaining=0;

                    loanSummaryEntity.setInstallmentsRemaining(installmentsRemaining);

                    Double installmentAmountPaid = installmentsPaid * emiAmount;

                    String formattedInstallmentAmountPaid = df.format(installmentAmountPaid);

                    installmentAmountPaid=Double.parseDouble(formattedInstallmentAmountPaid);

                    loanSummaryEntity.setInstallmentAmountPaid(installmentAmountPaid);

                    Double installmentAmountRemaining = installmentsRemaining * emiAmount;

                    String formattedInstallmentAmountRemaining = df.format(installmentAmountRemaining);

                    installmentAmountRemaining=Double.parseDouble(formattedInstallmentAmountRemaining);

                    loanSummaryEntity.setInstallmentAmountRemaining(installmentAmountRemaining);

                    Double otherChargesPaid = 0.0;

                    Double totalPaid = installmentAmountPaid + penaltyChargesPaid + otherChargesPaid;

                    Double getOtherChargesRemaining = 0.0;

                    loanSummaryEntity.setGetOtherChargesRemaining(getOtherChargesRemaining);

                    loanSummaryEntity.setOtherChargesPaid(otherChargesPaid);

                    loanSummaryEntity.setPenaltyChargesPaid(penaltyChargesPaid);

                    loanSummaryEntity.setPenaltyChargesRemaining(penaltyChargesRemaining);

                    Double totalOverdue = installmentAmountRemaining + penaltyChargesRemaining + getOtherChargesRemaining;


                    String formattedPaidAmount = df.format(totalPaid);

                    String formattedUnPaidAmount = df.format(totalOverdue);

                    totalPaid = Double.parseDouble(formattedPaidAmount);

                    totalOverdue = Double.parseDouble(formattedUnPaidAmount);

                    loanSummaryEntity.setTotalPaid(totalPaid);

                    loanSummaryEntity.setTotalOverdue(totalOverdue);

                }

            }

        } else {
            return "loan-not-found";

        }

        model.addAttribute("loanSummaryEntity", loanSummaryEntity);
        return "loan-summary-download";

    }

}