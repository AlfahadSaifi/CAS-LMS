package org.nucleus.utility.dtomapper;

import org.nucleus.entity.temporary.PersonInfoTemp;
import org.nucleus.dto.PersonInfoDTO;

public class PersonInfoMapper {

    public static PersonInfoDTO toDTO(PersonInfoTemp personInfoTemp){
        if(personInfoTemp ==null)return null;

        PersonInfoDTO personInfoDTO=new PersonInfoDTO();
        personInfoDTO.setPersonId(personInfoTemp.getPersonId());
        personInfoDTO.setDateOfBirth(personInfoTemp.getDateOfBirth());
        personInfoDTO.setFirstName(personInfoTemp.getFirstName());
        personInfoDTO.setMiddleName(personInfoTemp.getMiddleName());
        personInfoDTO.setLastName(personInfoTemp.getLastName());
        personInfoDTO.setFullName(personInfoTemp.getFullName());
        personInfoDTO.setPlaceOfBirth(personInfoTemp.getPlaceOfBirth());
        personInfoDTO.setGender(personInfoTemp.getGender());

        return personInfoDTO;
    }

    public static PersonInfoTemp toEntity(PersonInfoDTO personInfoDTO){
        if(personInfoDTO==null)return null;

        PersonInfoTemp personInfoTemp =new PersonInfoTemp();
        personInfoTemp.setPersonId(personInfoDTO.getPersonId());
        personInfoTemp.setDateOfBirth(personInfoDTO.getDateOfBirth());
        personInfoTemp.setFirstName(personInfoDTO.getFirstName());
        personInfoTemp.setMiddleName(personInfoDTO.getMiddleName());
        personInfoTemp.setLastName(personInfoDTO.getLastName());
        personInfoTemp.setFullName(personInfoDTO.getFullName());
        personInfoTemp.setPlaceOfBirth(personInfoDTO.getPlaceOfBirth());
        personInfoTemp.setGender(personInfoDTO.getGender());

        return personInfoTemp;
    }
}
