package org.nucleus.entity.meta;

import lombok.Data;
import org.nucleus.utility.enums.RecordStatus;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;

@Data
@Embeddable
public class MetaData {
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus;
    private Boolean activeInactiveFlag;
    private Date creationDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private Date authorizedDate;
    private String authorizedBy;

    public MetaData(){

    }


}