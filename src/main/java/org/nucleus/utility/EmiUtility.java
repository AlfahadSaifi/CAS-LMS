package org.nucleus.utility;

import org.apache.log4j.Logger;
import org.nucleus.entity.permanent.RepaySchedule;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmiUtility {

    public Double calculateEMI(double loanAmount, double tenure, int repaymentFrequency, double roi) {

        if(loanAmount<=0 || tenure<=0 || repaymentFrequency<=0 || roi<=0){
            return 0.0;
        }

        int installments = repaymentFrequency;
        double rv = 0;
        double rate = roi / (installments * 100);
        double totalInstallments = tenure * installments;
        double exp = Math.pow(1 + rate, totalInstallments);
        return (loanAmount * rate - (rv * rate) / exp) / (1 - 1 / exp);
    }

    public List<RepaySchedule> generateRepayScheduleForFixedRate(Double loanAmount, Double tenure, Integer repaymentFrequency, Double rate) {
        List<RepaySchedule> repayScheduleList = new ArrayList<>();
        double newRate=rate;
        double interestComponent;
        double principalComponent;
        double outstandingPrincipal;
        double principal = loanAmount;

        double installmentAmount = calculateEMI(loanAmount, tenure, repaymentFrequency, rate);

        int installments = repaymentFrequency;
        rate = rate / (installments * 100);

        double totalInstallments = tenure * installments;

        for (int installmentNo = 1; installmentNo < totalInstallments + 1; installmentNo++) {
            RepaySchedule repaySchedule= new RepaySchedule();
            interestComponent = principal * rate;
            principalComponent = installmentAmount - interestComponent;
            outstandingPrincipal = principal - principalComponent;


            repaySchedule.setOpeningBalance(Math.round(principal * 100.0) / 100.0);
            repaySchedule.setId((long) (installmentNo));
            repaySchedule.setInstallmentNumber(installmentNo);
            repaySchedule.setDueDate(Date.valueOf(LocalDate.now().plusMonths(installmentNo)));
            repaySchedule.setInterestComponent(Math.round(interestComponent * 100.0) / 100.0);
            repaySchedule.setInstallmentAmount(Math.round(installmentAmount * 100.0) / 100.0);
            repaySchedule.setPrincipalComponent(Math.round(principalComponent * 100.0) / 100.0);
            repaySchedule.setOutstandingBalancePrincipal((Math.round(outstandingPrincipal * 100.0) / 100.0)<0 ?0.0:Math.round(outstandingPrincipal * 100.0) / 100.0);
            repaySchedule.setEffectiveInterestRate(Math.round(rate*10000.0)/100.0);

            repayScheduleList.add(repaySchedule);

            principal = outstandingPrincipal;
        }

        return repayScheduleList;
    }


    public List<RepaySchedule> generateRepayScheduleForFixedRateForTenureChange(int installmentNumber,Double loanAmount, Double tenure, Integer repaymentFrequency, Double rate) {
        List<RepaySchedule> repayScheduleList = new ArrayList<>();
        double newRate=rate;
        double interestComponent;
        double principalComponent;
        double outstandingPrincipal;
        double principal = loanAmount;

        double installmentAmount = calculateEMI(loanAmount, tenure, repaymentFrequency, rate);

        int installments = repaymentFrequency;
        rate = rate / (installments * 100);

        double totalInstallments = tenure * installments;

        for (int installmentNo = installmentNumber; installmentNo < totalInstallments + installmentNumber; installmentNo++) {
            RepaySchedule repaySchedule= new RepaySchedule();
            interestComponent = principal * rate;
            principalComponent = installmentAmount - interestComponent;
            outstandingPrincipal = principal - principalComponent;
            repaySchedule.setOpeningBalance(Math.round(principal * 100.0) / 100.0);
            repaySchedule.setId((long) (installmentNo));
            repaySchedule.setInstallmentNumber(installmentNo);
            repaySchedule.setDueDate(Date.valueOf(LocalDate.now().plusMonths(installmentNo)));
            repaySchedule.setInterestComponent(Math.round(interestComponent * 100.0) / 100.0);
            repaySchedule.setInstallmentAmount(Math.round(installmentAmount * 100.0) / 100.0);
            repaySchedule.setPrincipalComponent(Math.round(principalComponent * 100.0) / 100.0);
            repaySchedule.setOutstandingBalancePrincipal((Math.round(outstandingPrincipal * 100.0) / 100.0)<0 ?0.0:Math.round(outstandingPrincipal * 100.0) / 100.0);
            repaySchedule.setEffectiveInterestRate(Math.round(rate*10000.0)/100.0);

            repayScheduleList.add(repaySchedule);

            principal = outstandingPrincipal;
        }
        return repayScheduleList;
    }
    public List<RepaySchedule> generateRepayScheduleForFloatingRate(Double loanAmount, Double tenure, Integer repaymentFrequency, Double rate) {
        List<RepaySchedule> repayScheduleList = new ArrayList<>();
        double newRate=rate;
        double interestComponent;
        double principalComponent;
        double outstandingPrincipal=1;
        double principal = loanAmount;
        LocalDate startDate = LocalDate.now();
        double installmentAmount = calculateEMI(loanAmount, tenure, repaymentFrequency, rate);
        int installments = repaymentFrequency;
        rate = rate / (installments * 100);
        double totalInstallments = tenure * installments;
        int installmentNo = 1;
        while(outstandingPrincipal>0){
            RepaySchedule repaySchedule= new RepaySchedule();
            interestComponent = principal * rate;
            principalComponent = installmentAmount - interestComponent;
            outstandingPrincipal = principal - principalComponent;
            if(installmentNo==totalInstallments/2){
                rate=newRate+10;
                rate=rate / (installments * 100);
                installmentAmount = calculateEMI(principal, tenure/2, repaymentFrequency, rate);
            }
            repaySchedule.setOpeningBalance(Math.round(principal * 100.0) / 100.0);
            repaySchedule.setId((long) (installmentNo));
            repaySchedule.setInstallmentNumber(installmentNo);
            // Calculate due date
            LocalDate dueDate=startDate;
            startDate = startDate.plusMonths(repaymentFrequency);
            repaySchedule.setDueDate(Date.valueOf(dueDate));
            repaySchedule.setInterestComponent(Math.round(interestComponent * 100.0) / 100.0);
            repaySchedule.setInstallmentAmount(Math.round(installmentAmount * 100.0) / 100.0);
            repaySchedule.setPrincipalComponent(Math.round(principalComponent * 100.0) / 100.0);
            repaySchedule.setOutstandingBalancePrincipal((Math.round(outstandingPrincipal * 100.0) / 100.0)<0 ?0.0:Math.round(outstandingPrincipal * 100.0) / 100.0);
            repaySchedule.setEffectiveInterestRate(Math.round(rate*10000.0)/100.0);
            repayScheduleList.add(repaySchedule);
            installmentNo++;
            principal = outstandingPrincipal;
        }
        return repayScheduleList;
    }

    private int calculateNumberOfInstallments(double loanAmount, double installmentAmount, int repaymentFrequency, double roi) {
        int installments = repaymentFrequency;
        double rv = 0;
        double x = ((Math.log10((installmentAmount-(roi*rv))/(installmentAmount-(roi*loanAmount))))/(Math.log10(1+roi))) ;
        System.out.println("asasza " + x);
        return (int)x;
    }
    public List<RepaySchedule> generateRepayScheduleForFixedRateByInstallmentAmount(int installmentNumber, Double loanAmount, Double installmentAmount, Integer repaymentFrequency, Double rate) {
        List<RepaySchedule> repayScheduleList = new ArrayList<>();
        double newRate=rate;
        double interestComponent;
        double principalComponent;
        double outstandingPrincipal;
        double principal = loanAmount;
        int installments = repaymentFrequency;
        rate = rate / (installments * 100);
        double totalInstallments = calculateNumberOfInstallments(loanAmount, installmentAmount, repaymentFrequency, rate);
        for (int installmentNo = installmentNumber; installmentNo < totalInstallments + installmentNumber + 1; installmentNo++) {
            RepaySchedule repaySchedule= new RepaySchedule();
            if(installmentNo>installmentNumber && repayScheduleList.get(repayScheduleList.size()-1).getOutstandingBalancePrincipal() < installmentAmount)
                installmentAmount = repayScheduleList.get(repayScheduleList.size()-1).getOutstandingBalancePrincipal();
            interestComponent = principal * rate;
            principalComponent = installmentAmount - interestComponent;
            outstandingPrincipal = principal - principalComponent;
            if(installmentNo == totalInstallments + installmentNumber)
                outstandingPrincipal = 0;
            repaySchedule.setOpeningBalance(Math.round(principal * 100.0) / 100.0);
            repaySchedule.setId((long) (installmentNo));
            repaySchedule.setInstallmentNumber(installmentNo);
            repaySchedule.setDueDate(Date.valueOf(LocalDate.now().plusMonths(installmentNo)));
            repaySchedule.setInterestComponent(Math.round(interestComponent * 100.0) / 100.0);
            repaySchedule.setInstallmentAmount(Math.round(installmentAmount * 100.0) / 100.0);
            repaySchedule.setPrincipalComponent(Math.round(principalComponent * 100.0) / 100.0);
            repaySchedule.setOutstandingBalancePrincipal((Math.round(outstandingPrincipal * 100.0) / 100.0)<0 ?0.0:Math.round(outstandingPrincipal * 100.0) / 100.0);
            repaySchedule.setEffectiveInterestRate(Math.round(rate*10000.0)/100.0);
            repayScheduleList.add(repaySchedule);
            principal = outstandingPrincipal;
        }
        return repayScheduleList;
    }

}

