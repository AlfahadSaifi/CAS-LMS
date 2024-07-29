package org.nucleus.entity.permanent;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.temporary.FinancialInfoTemp;
import org.nucleus.entity.temporary.LoanApplicationTemp;
import org.nucleus.entity.temporary.OccupationInfoTemp;
import org.nucleus.entity.temporary.PersonInfoTemp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "CUSTOMER_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "CUSTOMER_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Long customerId;

    private String cifNumber;
    private Long contactNumber;
    private String emailAddress;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personId")
    private PersonInfoTemp personInfoTemp;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "occupationId")
    private OccupationInfoTemp occupationInfoTemp;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "financialId")
    private FinancialInfoTemp financialInfoTemp;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "customerId")
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    private List<LoanApplicationTemp> loanApplications;
    public Customer(){
        loanApplications =new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", cifNumber='" + cifNumber + '\'' +
                ", contactNumber=" + contactNumber +
                ", emailAddress='" + emailAddress + '\'' +
                ", personInfoTemp=" + personInfoTemp +
                ", occupationInfoTemp=" + occupationInfoTemp +
                ", financialInfoTemp=" + financialInfoTemp +
                '}';
    }
}