package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "COUNTRY_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "COUNTRY_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class Country {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Integer id;

    private String countryGroup;
    private String countryName;
    private Integer countryIsdCode;
    private String countryIsoCode;
    private String nationality;
    private String region;
    private String status;

    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<State> states;

    @Embedded
    private MetaData metaData;

    public Country() {
        this.states = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryGroup='" + countryGroup + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryIsdCode=" + countryIsdCode +
                ", countryIsoCode='" + countryIsoCode + '\'' +
                ", nationality='" + nationality + '\'' +
                ", region='" + region + '\'' +
                ", status='" + status + '\'' +
                ", metaData=" + metaData +
                '}';
    }
}