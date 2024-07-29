package org.nucleus.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/** Developer : Monu Kumar*/

public class CustomerDTO {

    private Long customerId;

    private String cifNumber;

    @NotNull(message = "Contact Number is required")
    private Long contactNumber;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email address")
    private String emailAddress;

    @Valid
    private PersonInfoDTO personInfoDTO;

    @Valid
    private OccupationInfoDTO occupationInfoDTO;

    @Valid
    private FinancialInfoDTO financialInfoDTO;

    private List<AddressDTO> addressDTOS;

    private List<LoanApplicationDTO> loanApplicationDTOS;

    public CustomerDTO(){
        loanApplicationDTOS=new ArrayList<>();
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCifNumber() {
        return cifNumber;
    }

    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public PersonInfoDTO getPersonInfoDTO() {
        return personInfoDTO;
    }

    public void setPersonInfoDTO(PersonInfoDTO personInfoDTO) {
        this.personInfoDTO = personInfoDTO;
    }

    public OccupationInfoDTO getOccupationInfoDTO() {
        return occupationInfoDTO;
    }

    public void setOccupationInfoDTO(OccupationInfoDTO occupationInfoDTO) {
        this.occupationInfoDTO = occupationInfoDTO;
    }

    public FinancialInfoDTO getFinancialInfoDTO() {
        return financialInfoDTO;
    }

    public void setFinancialInfoDTO(FinancialInfoDTO financialInfoDTO) {
        this.financialInfoDTO = financialInfoDTO;
    }

    public List<AddressDTO> getAddressDTOS() {
        return addressDTOS;
    }

    public void setAddressDTOS(List<AddressDTO> addressDTOS) {
        this.addressDTOS = addressDTOS;
    }

    public List<LoanApplicationDTO> getLoanApplicationDTOS() {
        return loanApplicationDTOS;
    }

    public void setLoanApplicationDTOS(List<LoanApplicationDTO> loanApplicationDTOS) {
        this.loanApplicationDTOS = loanApplicationDTOS;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerId=" + customerId +
                ", cifNumber='" + cifNumber + '\'' +
                ", contactNumber=" + contactNumber +
                ", emailAddress='" + emailAddress + '\'' +
                ", personInfoDTO=" + personInfoDTO +
                ", occupationInfoDTO=" + occupationInfoDTO +
                ", financialInfoDTO=" + financialInfoDTO +
                '}';
    }
}