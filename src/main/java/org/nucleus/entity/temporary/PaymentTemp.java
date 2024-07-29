package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.Currency;
import org.nucleus.utility.enums.PaymentMode;
import org.nucleus.utility.enums.ReceiptStatus;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "PAYMENT_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "PAYMENT_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class PaymentTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long receiptId;

    private String payeeName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "receivablePayableId")
    private ReceivablePayableTemp receivablePayable;

    private double paymentAmount;

    @Enumerated(EnumType.STRING)
    private Currency paymentCurrency;

    private String loanAccountNumber;
    private Date paymentDate;
    private String payoutBankAccount;

    @Enumerated(EnumType.STRING)
    private ReceiptStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    @Embedded
    private TempMetaData metaData;
    private String paidTo;
}
