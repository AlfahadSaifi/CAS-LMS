package org.nucleus.service;

import org.nucleus.dao.LoanClosureDAO;
import org.nucleus.dao.LoanClosureDAOTemp;
import org.nucleus.dto.CheckerEarlyClosureDTO;
import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.dto.MakerEarlyClosureDTO;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class EarlyClosureServiceImpl implements EarlyClosureService
{
    private final LoanClosureDAOTemp loanClosureDaoTemp;
    private final LoanClosureDAO loanClosureDAO;
    private final LoanAccountService loanAccountService;
    private final CustomerService customerService;
    @Autowired
    public EarlyClosureServiceImpl(LoanClosureDAOTemp loanClosureDaoTemp, LoanClosureDAO loanClosureDAO,LoanAccountService loanAccountService, CustomerService customerService)
    {
        this.loanClosureDaoTemp = loanClosureDaoTemp;
        this.loanClosureDAO = loanClosureDAO;
        this.loanAccountService = loanAccountService;
        this.customerService = customerService;
    }

    // Create in temporary table
    @Override
    public Long createLoanClosureTemp(LoanClosureDTO loanClosureDTO)
    {
        System.out.println("Creating early loan closure : in service");
        if(loanClosureDTO==null)
        {
            System.out.println("Creating early loan closure 2");
            return null;
        }
        else
        {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            MetaData metadata=new MetaData();
            metadata.setRecordStatus(RecordStatus.N);
            metadata.setCreatedBy(userDetails.getUsername());// Setting UserName
            metadata.setCreationDate(Date.valueOf(LocalDate.now())); // Setting Current Date
            loanClosureDTO.setMetaData(metadata);
            System.out.println("Creating early loan closure 3");
            return loanClosureDaoTemp.addEarlyClosureData(loanClosureDTO);
        }
    }

    @Override
    public boolean createLoanClosure(LoanClosureDTO loanClosureDTO)
    {
        if(loanClosureDTO==null) {
            return false;
        }
        else
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            MetaData metaData=new MetaData();
            metaData.setRecordStatus(RecordStatus.A);
            metaData.setCreatedBy(userDetails.getUsername());// Setting UserName
            metaData.setCreationDate(Date.valueOf(LocalDate.now())); // Setting Current Date
            metaData.setAuthorizedBy(userDetails.getUsername());
            metaData.setAuthorizedDate(Date.valueOf(LocalDate.now()));
            loanClosureDTO.setMetaData(metaData);
            return loanClosureDAO.save(loanClosureDTO);
        }
    }

    // Read from temporary table
    @Override
    public LoanClosureDTO getLoanClosureTempById(Long id)
    {
        if(id==null)
            return null;
        return loanClosureDaoTemp.getLoanClosureDetail(id);
    }

    // Delete from permanent table
    @Override
    public boolean deleteLoanClosure(Long id)
    {
        if(id==null)
            return false;
        return loanClosureDaoTemp.delete(id);
    }
    @Override
    public MakerEarlyClosureDTO getAllMakerFields(String loanAccountNumber)
    {
        MakerEarlyClosureDTO maker=new MakerEarlyClosureDTO();

        LoanAccountDTO loanAccountDTO=loanAccountService.getByAccountNumber(loanAccountNumber);
        String customerName=customerService
                .getByCIFNumber(loanAccountDTO.getCifNumber())
                .getPersonInfoDTO()
                .getFullName();

        maker.setLoanAccountNumber(loanAccountNumber);// Loan Account Number
        maker.setCustomerName(customerName); // Customer Name
        maker.setBalanceInstallment(loanAccountDTO.getNumberOfInstallmentUnbilled()); // Balance Installment
        maker.setLoanAmount(loanAccountDTO.getFinalSanctionedAmount()); // Loan Amount
        double balancePrinciple = loanAccountDTO.getFinalSanctionedAmount()-loanAccountDTO.getLoanAmountPaidByCustomer();
        maker.setBalancePrinciple(balancePrinciple); // Balance Principal
        maker.setTotalDuePrincipal(balancePrinciple); // Total Due Principal
        maker.setDueCharges(0.0); // Charges set to zero. This Functionality depends from bank to bank
        maker.setTotalClosureAmount(balancePrinciple+0.0); // Balance principal + due charges
        return maker;
    }

    @Override
    public LoanAccountDTO getLoanAccount(String loanAccountNumber)
    {
        return loanAccountService.getByAccountNumber(loanAccountNumber);
    }

    @Override
    public LoanClosureDTO getLoanClosureByAccountId(Long loanAccountId)
    {
        return loanClosureDaoTemp.findLoanClosureByLoanAccountId(loanAccountId);
    }
    @Override
    public LoanClosureDTO getLoanClosureByAccountNumber(String loanAccountNumber)
    {
        LoanAccountDTO loanAccountDTO=loanAccountService.getByAccountNumber(loanAccountNumber);
        System.out.println(loanAccountDTO.toString());
        return loanClosureDaoTemp.findLoanClosureByLoanAccountId(loanAccountDTO.getLoanAccountId());
    }
    @Override
    public CheckerEarlyClosureDTO getAllCheckerFields(String loanAccountNumber)
    {
        CheckerEarlyClosureDTO checker=new CheckerEarlyClosureDTO();
        LoanClosureDTO loanClosureDTO;
        LoanAccountDTO loanAccountDTO=loanAccountService.getByAccountNumber(loanAccountNumber);
        if(loanAccountDTO==null)
        {
            return null;
        }
        else
        {
            loanClosureDTO = loanClosureDaoTemp.findLoanClosureByLoanAccountId(loanAccountDTO.getLoanAccountId());
            if(loanClosureDTO==null)return null;
            System.out.println(loanClosureDTO);
            checker.setLoanClosureId(loanClosureDTO.getLoanClosureId());
            checker.setClosureStatus(loanClosureDTO.getClosureStatus());
            checker.setFinalSanctionedAmount(loanAccountDTO.getFinalSanctionedAmount());
            checker.setLoanClosureDate(loanClosureDTO.getLoanClosureDate());
            checker.setLoanAccountNumber(loanAccountNumber);
            checker.setLoanAmountPaidByCustomer(loanAccountDTO.getLoanAmountPaidByCustomer());
            checker.setDueDate(Date
                    .valueOf(
                            loanClosureDTO
                                    .getMetaData()
                                    .getCreationDate()
                                    .toLocalDate()
                                    .plusMonths(1)
                    )
            );
            checker.setMetaData(loanClosureDTO.getMetaData());
            return checker;
        }
    }

    @Override
    public boolean updateLoanClosureTemp(LoanClosureDTO loanClosureDTO)
    {
        if(loanClosureDTO==null)
            return false;
        else
        {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            loanClosureDTO.getMetaData().setRecordStatus(RecordStatus.NR);
            loanClosureDTO.getMetaData().setModifiedBy(userDetails.getUsername());// Setting UserName
            loanClosureDTO.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now())); // Setting Current Date
            return loanClosureDaoTemp.update(loanClosureDTO);
        }
    }

    @Override
    public Long findLoanClosureByLoanAccountId(Long loanClosureId)
    {
        return loanClosureDaoTemp.getLoanClosureDetail(loanClosureId).getLoanAccountDTO().getLoanAccountId();
    }

    @Override
    public boolean updateLoanAccount(LoanAccountDTO loanAccountDTO)
    {
        return loanAccountService.updateLoanAccount(loanAccountDTO);
    }

    @Override
    public boolean deleteLoanClosureByDTO(LoanClosureDTO loanClosureDTO)
    {
        return loanClosureDaoTemp.delete(loanClosureDTO);
    }

}
