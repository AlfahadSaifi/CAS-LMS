package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.ProfessionType;

import javax.persistence.*;
@Data
@Entity
@Table(name = "OCCUPATION_INFO_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "OCCUPATION_INFO_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class OccupationInfoTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long occupationId;
    private Integer ageOfRetirement;
    private Integer totalYearOfExperience;
    private ProfessionType professionType;
    private String designation;
    private String organizationName;
    private String organizationLocation;
}