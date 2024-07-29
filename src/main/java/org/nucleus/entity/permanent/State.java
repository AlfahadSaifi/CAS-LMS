package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "STATE_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "STATE_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class State {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Integer id;

    private String stateCode;
    private String stateName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String region;
    private Boolean isUnionTerritory;

    @OneToMany(mappedBy = "state", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<City> cities;

    @Embedded
    private MetaData metaData;

    public State() {
        this.cities = new ArrayList<>();
        metaData = new MetaData();
    }
}
