package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.Customer;
import org.nucleus.entity.permanent.LoanProduct;
import org.nucleus.utility.enums.LoanStatus;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "LOAN_APPLICATION_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "LOAN_APPLICATION_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class LoanApplicationTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long loanId;
    @Column(unique = true)
    private String loanApplicationId;
    private Date applicationCreationDate;
    private Double loanAmount;
    private Integer tenure;
    private String loanAccountNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private LoanProduct loanProduct;
    private String branch;
    private String tenureIn;
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus = LoanStatus.PENDING;
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
    @Embedded
    private TempMetaData tempMetaData;
    public LoanApplicationTemp(){
        tempMetaData=new TempMetaData();
    }
}