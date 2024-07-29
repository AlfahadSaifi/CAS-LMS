package org.nucleus.controller;

import org.nucleus.dto.*;
import org.nucleus.entity.permanent.JasperEntity;
import org.nucleus.service.CustomerService;
import org.nucleus.service.EmployeeService;
import org.nucleus.service.LoanAccountService;
import org.nucleus.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DisbursalController {
    private final LoanAccountService loanAccountService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final LoanApplicationService loanApplicationService;

    @Autowired
    public DisbursalController(LoanAccountService loanAccountService, CustomerService customerService, EmployeeService employeeService, LoanApplicationService loanApplicationService) {
        this.loanAccountService = loanAccountService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.loanApplicationService = loanApplicationService;
    }

    @RequestMapping("disbursal-report")
    public String getDisbursalReport(){
        return "loan-disbursal";
    }

    @GetMapping("loanForm.do")
    public String applyLoan(@RequestParam("loanAccountNumber") String loanAccountNumber , Model model){

        JasperEntity jasperEntity = new JasperEntity();
        model.addAttribute("loanAccountNumber",loanAccountNumber);
        jasperEntity.setLoanAccountNumber(loanAccountNumber);

        if(loanAccountNumber!=null) {
            LoanAccountDTO loanAccountDTO = loanAccountService.getByAccountNumber(loanAccountNumber);
            if (loanAccountDTO != null) {

                model.addAttribute("originalEmiAmount", loanAccountDTO.getOriginalEmiAmount());
                jasperEntity.setOriginalEmiAmount(loanAccountDTO.getOriginalEmiAmount());

                model.addAttribute("interestRate", loanAccountDTO.getInterestRate());
                jasperEntity.setInterestRate(loanAccountDTO.getInterestRate());

                model.addAttribute("originalTenure", loanAccountDTO.getOriginalTenure());
                jasperEntity.setOriginalTenure(loanAccountDTO.getOriginalTenure());

                model.addAttribute("loanAmountDisbursed", loanAccountDTO.getLoanAmountDisbursed());
                jasperEntity.setLoanAmountDisbursed(loanAccountDTO.getLoanAmountDisbursed());

                model.addAttribute("disbursalDate", loanAccountDTO.getDisbursalDate());
                jasperEntity.setDisbursalDate(loanAccountDTO.getDisbursalDate());

                model.addAttribute("finalSanctionedAmount", loanAccountDTO.getFinalSanctionedAmount());
                jasperEntity.setFinalSanctionedAmount(loanAccountDTO.getFinalSanctionedAmount());

                model.addAttribute("loanSanctionDate", loanAccountDTO.getLoanSanctionDate());
                jasperEntity.setLoanSanctionDate(loanAccountDTO.getLoanSanctionDate());

                model.addAttribute("repaymentFrequency", loanAccountDTO.getRepaymentFrequency());
                jasperEntity.setRepaymentFrequency(loanAccountDTO.getRepaymentFrequency());

                model.addAttribute("numberOfInstallment", loanAccountDTO.getNumberOfInstallment());
                jasperEntity.setNumberOfInstallment(loanAccountDTO.getNumberOfInstallment());

                model.addAttribute("loanProduct", loanAccountDTO.getLoanProduct());
                jasperEntity.setLoanProduct(loanAccountDTO.getLoanProduct());


                model.addAttribute("loanAmountPaidByCustomer", loanAccountDTO.getLoanAmountPaidByCustomer());
                jasperEntity.setLoanAmountPaidByCustomer(loanAccountDTO.getLoanAmountPaidByCustomer());


                model.addAttribute("productType", loanAccountDTO.getProductType());
                jasperEntity.setProductType(loanAccountDTO.getProductType());


                String cifNumber = loanAccountDTO.getCifNumber();
                jasperEntity.setCifNumber(loanAccountDTO.getCifNumber());


                CustomerDTO customerDTO = customerService.getCustomerWithAddressByCIFNumber(cifNumber);

                if (customerDTO != null) {
                    model.addAttribute("emailAddress", customerDTO.getEmailAddress());
                    jasperEntity.setEmailAddress(customerDTO.getEmailAddress());

                    model.addAttribute("contactNumber", customerDTO.getContactNumber());
                    jasperEntity.setContactNumber(customerDTO.getContactNumber());

                    model.addAttribute("cifNumber", customerDTO.getCifNumber());

                    Long customerId = customerDTO.getCustomerId();
                    if (customerId != 0) {
                        PersonInfoDTO personInfoDTO = customerDTO.getPersonInfoDTO();
                        if (personInfoDTO != null) {
                            model.addAttribute("dateOfBirth", personInfoDTO.getDateOfBirth());
                            jasperEntity.setDateOfBirth(personInfoDTO.getDateOfBirth());

                            model.addAttribute("name1", personInfoDTO.getFullName());
                            jasperEntity.setFirstName(personInfoDTO.getFirstName());

                            if(customerDTO.getAddressDTOS()!=null && !customerDTO.getAddressDTOS().isEmpty()){
                                AddressDTO addressDTO = customerDTO.getAddressDTOS().get(0);
                                model.addAttribute("zipCode", addressDTO.getPinCode());
                                jasperEntity.setPinCode(addressDTO.getPinCode());

                                model.addAttribute("country", addressDTO.getCountryName());
                                jasperEntity.setCountry(addressDTO.getCountryName()); // change to country name

                                model.addAttribute("state", addressDTO.getStateName());
                                jasperEntity.setState(addressDTO.getStateName()); // change to state name

                                model.addAttribute("city", addressDTO.getCityName());
                                jasperEntity.setCity(addressDTO.getCityName()); // change to city name

                                model.addAttribute("address", addressDTO.getFullAddress());


                                jasperEntity.setFullAddress(addressDTO.getFullAddress());
                            }
                        }
                    }

                    LoanApplicationDTO loanApplicationDTO = loanApplicationService.readPermanentByLoanAccountNumber(loanAccountNumber);
                    if (loanApplicationDTO != null) {
                        model.addAttribute("branch", loanApplicationDTO.getBranch());
                        jasperEntity.setBranch(loanApplicationDTO.getBranch());
                    }
                }
            }
            else {
                return "loan-disbursal-error";
            }
        }
        employeeService.saveEmployee(jasperEntity);
        return "loan-disbursal-download";
    }
}