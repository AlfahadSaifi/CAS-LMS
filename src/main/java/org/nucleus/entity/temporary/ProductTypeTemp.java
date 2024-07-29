package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.FundBasedFlag;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PRODUCT_TYPE_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "PRODUCT_TYPE_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class ProductTypeTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long productTypeId;

    private String productTypeCode;

    private String description;

    @Enumerated(EnumType.STRING)
    private FundBasedFlag fundBasedFlag;

    private Character securedFlag; // Y or N

//    @OneToMany
//    private List<LoanProductTemp> products;

    @Embedded
    private TempMetaData metaData;

    public ProductTypeTemp() {
        metaData = new TempMetaData();
    }
}
