package org.nucleus.utility;

import org.nucleus.dto.*;
import org.nucleus.utility.enums.ReceivablePayableStatus;

import java.text.DecimalFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AllocationCalculation {

    private AllocationCalculation(){}

    public static Double[] distributeAmount(double totalAmount, Double principalAmount, Double interestAmount, Double penaltyAmount) {
        Double[] amounts = new Double[3];

        //define priority
        Double[] priorities = {processDoubleValue(penaltyAmount), processDoubleValue(interestAmount), processDoubleValue(principalAmount)};

        //remaining amount to distribute between principal, interest and penalty
        final Double[] remainingAmount = {totalAmount};

        IntStream.range(0, priorities.length)
                .filter(i -> remainingAmount[0] > 0)
                .forEach(i -> {
                    double amountToDeduct = Math.min(remainingAmount[0], priorities[i]);
                    amounts[i] = amountToDeduct;
                    remainingAmount[0] -= amountToDeduct;
                });

        //handle null case if any exists
        for(int i = 0 ; i<2 ;i ++){
            amounts [i] = processDoubleValue(amounts[i]);
            if(amounts[i]==null)amounts[i]=0.00;
            System.out.println("amount to be paid "+ amounts[i]);
        }

        //format the numbers to 2 precision
        formatAndConvertToDouble(amounts);

        return amounts;
    }

    public static Double processDoubleValue(Double value) {
        //handle null due to wrapper
        if(value == null)return 0.0;
        return value;
    }

    public static Integer processIntegerValue(Integer value){
        //handle null due to wrapper
        if(value == null)return 0;
        return value;
    }

    public static void updateReceivable(ReceivablePayableDTO receivablePayableDTO, Double[] distributionAmount){

        //getting the previous billed interest, principal, and penalty
        double interestReceivedTillDate = processDoubleValue(receivablePayableDTO.getInterestComponentReceived());
        double principalReceivedTillDate = processDoubleValue(receivablePayableDTO.getPrincipalComponentReceived());
        double penaltyReceivedTillDate = processDoubleValue(receivablePayableDTO.getPenalty());

        //update them with the new amount received
        interestReceivedTillDate +=processDoubleValue(distributionAmount[1]);
        principalReceivedTillDate +=processDoubleValue(distributionAmount[2]);
        penaltyReceivedTillDate +=processDoubleValue(distributionAmount[0]);

        //update the object accordingly
        receivablePayableDTO.setInterestComponentReceived(interestReceivedTillDate);
        receivablePayableDTO.setPrincipalComponentReceived(principalReceivedTillDate);
        receivablePayableDTO.setPenalty(penaltyReceivedTillDate);

        //update the receivable if the amount paid is equal to the amount required
        if(processDoubleValue(receivablePayableDTO.getPrincipalComponentReceived()) - processDoubleValue(receivablePayableDTO.getPrincipalComponent()) <= 5.00){
            receivablePayableDTO.setReceivablePayableStatus(ReceivablePayableStatus.PAID);
        }

    }

    public static LoanAccountDTO updateLoanAccount(LoanAccountDTO loanAccountDTO, Double principalAmount, boolean paidFlag){

        //get the number of installment billed till date and add 1 to it
        if(paidFlag) {
            Integer numberOfInstallmentBilledTillDate = loanAccountDTO.getNumberOfInstallmentBilled();
            loanAccountDTO.setNumberOfInstallmentBilled(processIntegerValue(numberOfInstallmentBilledTillDate) + 1);

            //get the number of un-billed and subtract 1 from it
            Integer numberOfInstallmentUnBilledTillDate = loanAccountDTO.getNumberOfInstallmentUnbilled();
            if (processIntegerValue(numberOfInstallmentUnBilledTillDate) > 0)
                loanAccountDTO.setNumberOfInstallmentUnbilled(processIntegerValue(numberOfInstallmentUnBilledTillDate) - 1);
        }
        Double totalPrincipalPaid = loanAccountDTO.getLoanAmountPaidByCustomer();
        System.out.println(totalPrincipalPaid+"---------------------->");
        loanAccountDTO.setLoanAmountPaidByCustomer(processDoubleValue(totalPrincipalPaid)+processDoubleValue(principalAmount));

        if(loanAccountDTO.getFinalSanctionedAmount() - loanAccountDTO.getLoanAmountPaidByCustomer() <= 5){
            loanAccountDTO.setLoanAmountPaidByCustomer(loanAccountDTO.getFinalSanctionedAmount());
        }

        return loanAccountDTO;
    }

    public static AllocationDTO createAllocationDTO(LoanAccountDTO loanAccountDTO,Double [] distributionAmount, ReceiptDTO receiptDTO){

        AllocationDTO allocationDTO = new AllocationDTO();
        allocationDTO.setPrincipalComponentReceived(distributionAmount[2]);
        allocationDTO.setInterestComponentReceived(distributionAmount[1]);
        List<PenaltyDTO>penaltyDTOList = new ArrayList<>();
        PenaltyDTO penaltyDTO = new PenaltyDTO();
        penaltyDTO.setPenaltyAmount(distributionAmount[0]);
        penaltyDTO.setDescription("Late penalty charge");
        penaltyDTOList.add(penaltyDTO);
        allocationDTO.setPenaltyDTOS(penaltyDTOList);
        allocationDTO.setDepositDate(receiptDTO.getInstrumentDate());
        allocationDTO.setLoanAccount(loanAccountDTO);

        return allocationDTO;
    }

    public static int calculateNumberOfBatches(int batchSize, int totalNumberOfRows){
        return (int)(Math.ceil((totalNumberOfRows*1.0) / (batchSize*1.0)));
    }

    public static double calculatePenalty(ReceivablePayableDTO receivablePayableDTO, ReceiptDTO receiptDTO){
        return (double) Period.between(receivablePayableDTO.getReceivablePayableDate().toLocalDate(), receiptDTO.getInstrumentDate().toLocalDate()).getDays()*1000;
    }

    public static void formatAndConvertToDouble(Double[] numbers) {
        // Create a DecimalFormat object with pattern for 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println(numbers[0]+" "+numbers[1]+" "+numbers[2]+"---------------------------------------------------->");
        // Format each number to have 2 decimal places and update the array
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i]==null)numbers[i]=0.00;
            double formattedNumber = Double.parseDouble(df.format(numbers[i]));
            numbers[i] = formattedNumber;
        }
    }

}
