package org.nucleus.entity.temporary;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.utility.enums.Gender;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "PERSON_INFO_TEMP_TBL_BATCH_6")
@TableGenerator(name="ID_TEMP_TABLE_GEN_BATCH_6",pkColumnValue = "PERSON_INFO_TEMP_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class PersonInfoTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TEMP_TABLE_GEN_BATCH_6")
    private Long personId;
    private Date dateOfBirth;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String placeOfBirth;
    private Gender gender;
}
