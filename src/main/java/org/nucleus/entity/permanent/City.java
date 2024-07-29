package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CITY_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "CITY_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class City {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Integer id;

    private String country;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    private String cityName;
    private String cityCode;
    private String stdCode;
    private String cityMICRCode;
    private String locationType;
    @Embedded
    private MetaData metaData;
}
