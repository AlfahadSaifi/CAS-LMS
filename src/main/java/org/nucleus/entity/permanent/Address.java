package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ADDRESS_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "ADDRESS_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Integer addressId;
    private String addressType;
    private String fullAddress;
    private String countryName;
    private String stateName;
    private String cityName;
    private String flatNumber;
    private Integer pinCode;
    private String region;
    private String district;
}