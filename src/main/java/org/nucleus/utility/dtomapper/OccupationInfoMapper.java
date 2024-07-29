package org.nucleus.utility.dtomapper;

import org.nucleus.entity.temporary.OccupationInfoTemp;
import org.nucleus.dto.OccupationInfoDTO;
public class OccupationInfoMapper {


    public static OccupationInfoTemp toEntity(OccupationInfoDTO occupationInfoDTO){
        if(occupationInfoDTO==null)return null;
        OccupationInfoTemp occupationInfoTemp =new OccupationInfoTemp();

        occupationInfoTemp.setOccupationId(occupationInfoDTO.getOccupationId());
        occupationInfoTemp.setAgeOfRetirement(occupationInfoDTO.getAgeOfRetirement());
        occupationInfoTemp.setTotalYearOfExperience(occupationInfoDTO.getTotalYearOfExperience());
        occupationInfoTemp.setProfessionType(occupationInfoDTO.getProfessionType());
        occupationInfoTemp.setDesignation(occupationInfoDTO.getDesignation());
        occupationInfoTemp.setOrganizationName(occupationInfoDTO.getOrganizationName());
        occupationInfoTemp.setOrganizationLocation(occupationInfoDTO.getOrganizationLocation());

        return occupationInfoTemp;
    }

    public static OccupationInfoDTO toDTO(OccupationInfoTemp occupationInfoTemp){
        if(occupationInfoTemp ==null)return null;
        OccupationInfoDTO occupationInfoDTO=new OccupationInfoDTO();

        occupationInfoDTO.setOccupationId(occupationInfoTemp.getOccupationId());
        occupationInfoDTO.setAgeOfRetirement(occupationInfoTemp.getAgeOfRetirement());
        occupationInfoDTO.setTotalYearOfExperience(occupationInfoTemp.getTotalYearOfExperience());
        occupationInfoDTO.setProfessionType(occupationInfoTemp.getProfessionType());
        occupationInfoDTO.setDesignation(occupationInfoTemp.getDesignation());
        occupationInfoDTO.setOrganizationName(occupationInfoTemp.getOrganizationName());
        occupationInfoDTO.setOrganizationLocation(occupationInfoTemp.getOrganizationLocation());

        return occupationInfoDTO;
    }
}
