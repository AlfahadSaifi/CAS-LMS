package org.nucleus.entity.permanent;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.utility.enums.LoanSecurityType;

import javax.persistence.*;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "LOAN_PRODUCT_TBL_BATCH_6")
@TableGenerator(name = "ID_TABLE_GEN_BATCH_6", pkColumnValue = "LOAN_PRODUCT_TBL_BATCH_6", initialValue = 100000, allocationSize = 1)
public class LoanProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Long productId;

    private String productCode;
    private String productName;
    private String productDescription;

    @Enumerated(EnumType.STRING)
    private LoanSecurityType securityType;
    private Double rateOfInterest;

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "productTypeId")
    private ProductType productType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "eligibilityPolicyId")
    private EligibilityPolicy eligibilityPolicy;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "repaymentPolicyId")
    private RepaymentPolicy repaymentPolicy;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "policyCode")
    private ChargePolicy chargePolicy;

    @Embedded
    private MetaData metaData;
}