package org.nucleus.entity.permanent;

import lombok.Data;
import org.nucleus.entity.meta.MetaData;
import org.nucleus.utility.enums.Gender;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "PERSON_INFO_TBL_BATCH_6")
@TableGenerator(name="ID_TABLE_GEN_BATCH_6",pkColumnValue = "PERSON_INFO_TBL_BATCH_6",initialValue=100000, allocationSize=1)
public class PersonInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="ID_TABLE_GEN_BATCH_6")
    private Long personId;
    private Date dateOfBirth;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String placeOfBirth;
    private Gender gender;

    @OneToOne
    private Customer customer;
    @Embedded
    private MetaData metaData;
}
