package org.nucleus.entity.permanent;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.temporary.RepayScheduleTemp;
import org.nucleus.utility.enums.LoanStatus;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "LOAN_APPLICATION_TBL_BATCH_6")
@TableGenerator(name = "ID_TABLE_GEN_BATCH_6", pkColumnValue = "LOAN_APPLICATION_TBL_BATCH_6", initialValue = 100000, allocationSize = 1)
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_TABLE_GEN_BATCH_6")
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
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "loanId")
    private List<RepaySchedule> repaySchedules;
    @Embedded
    private MetaData metaData;

    public LoanApplication() {
        metaData = new MetaData();
    }
}