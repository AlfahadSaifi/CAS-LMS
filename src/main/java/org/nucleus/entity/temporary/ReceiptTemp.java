package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.entity.permanent.ReceivablePayable;
import org.nucleus.utility.enums.Currency;
import org.nucleus.utility.enums.PaymentMode;
import org.nucleus.utility.enums.ReceiptBasis;
import org.nucleus.utility.enums.ReceiptStatus;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "RECEIPT_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "RECEIPT_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class ReceiptTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long receiptId;

    private String loanAccountNumber;
    private Double transactionAmount;
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Date dateOfReceipt;
    private Double requiredAmount;
    private String instrumentNumber;
    private Date instrumentDate;
    private String receivedfrom;
    @Enumerated(EnumType.STRING)
    private ReceiptBasis receiptbasis;
    private String bankName;

    @Enumerated(EnumType.STRING)
    private ReceiptStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "receivablePayableId")
    private ReceivablePayable receivablePayable;
    @Embedded
    private TempMetaData metaData;
}