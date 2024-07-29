package org.nucleus.utility.dtomapper.address;

import org.nucleus.dto.CountryDTO;
import org.nucleus.dto.StateDTO;
import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;
import org.nucleus.service.address.CountryService;
import org.nucleus.utility.dtomapper.MetaDataMapper;
import org.nucleus.utility.temptoperm.CountryTempToPermImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class StateMapperImpl implements StateMapper {

    @Autowired
    private CountryTempToPermImpl countryTempToPerm;

    @Autowired
    private CountryDTOMapper countryDTOMapper;
    @Autowired
    private CountryDTOMapperTemp countryDTOMapperTemp;

    @Autowired
    private CountryService countryService;


    @Override
    public StateTemp stateDtoToStateEntity(StateDTO stateDTO) {
        if(stateDTO==null) return null;

        StateTemp state = new StateTemp();
        state.setId(stateDTO.getId());
        state.setStateCode(stateDTO.getStateCode());
        state.setStateName(stateDTO.getStateName());
        state.setCountryName(stateDTO.getCountry().getCountryName());
        state.setRegion(stateDTO.getRegion());
        //state.setIsUnionTerritory(stateDTO.getUnionTerritory());
        state.setMetaDataTemp(stateDTO.getMetaDataTemp());
        return  state;
    }

    @Override
    public StateDTO stateEntityToStateDto(StateTemp state) {
        if(state==null) return null;
        StateDTO stateDTO = new StateDTO();
        stateDTO.setId(state.getId());
        stateDTO.setStateCode(state.getStateCode());
        stateDTO.setStateName(state.getStateName());
        //get country by name
        //state DTO has country DTO reference
        //State temp has String country name
        CountryDTO countryDTO = countryService.getCountryByCountryName(state.getCountryName());
        stateDTO.setCountry(countryDTO);
        stateDTO.setRegion(state.getRegion());
       // stateDTO.setUnionTerritory(state.getIsUnionTerritory());
        stateDTO.setMetaDataTemp(state.getMetaDataTemp());


        return  stateDTO;
    }
    @Override
    public State stateDtoToStatePermanent(StateDTO stateDTO) {
        if(stateDTO==null)return null;
        State state = new State();
        state.setId(stateDTO.getId());
        state.setStateCode(stateDTO.getStateCode());
        state.setStateName(stateDTO.getStateName());
        state.setCountry(countryDTOMapper.dtoToCountry(stateDTO.getCountry()));
        state.setRegion(stateDTO.getRegion());
        state.setIsUnionTerritory(stateDTO.getUnionTerritory());
        state.setMetaData(MetaDataMapper.tempToMetaData(stateDTO.getMetaDataTemp()));

        return  state;
    }

    @Override
    public StateDTO statePermanentToStateDto(State state) {
        if (state==null) return  null;

        StateDTO stateDTO = new StateDTO();
        stateDTO.setId(state.getId());
        stateDTO.setStateCode(state.getStateCode());
        stateDTO.setStateName(state.getStateName());
        stateDTO.setRegion(state.getRegion());
        state.setCountry(state.getCountry());
        stateDTO.setUnionTerritory(state.getIsUnionTerritory());
        stateDTO.setMetaDataTemp(MetaDataMapper.metaDataToTemp(state.getMetaData()));
        return  stateDTO;
    }


    @Override
    public List<StateDTO> convertStateTempListToDTOList(List<StateTemp> stateTempList) {
        List<StateDTO> stateDTOList = new ArrayList<>();
        for (StateTemp stateTemp : stateTempList) {
            StateDTO stateDTO = new StateDTO();
            stateDTO.setId(stateTemp.getId());
            stateDTO.setStateCode(stateTemp.getStateCode());
            stateDTO.setStateName(stateTemp.getStateName());

            //get country by name
            CountryDTO countryDTO = countryService.getCountryByCountryName(stateTemp.getCountryName());
            stateDTO.setCountry(countryDTO);
            stateDTO.setRegion(stateTemp.getRegion());
            //stateDTO.setUnionTerritory(stateTemp.getIsUnionTerritory());
            stateDTO.setMetaDataTemp(stateTemp.getMetaDataTemp());
            stateDTOList.add(stateDTO);
        }
        return stateDTOList;
    }


    @Override
    public List<StateDTO> convertStateListToDTOList(List<State> stateList) {
        List<StateDTO> stateDTOList = new ArrayList<>();
        for (State state : stateList) {
            StateDTO stateDTO = new StateDTO();
            stateDTO.setId(state.getId());
            stateDTO.setStateCode(state.getStateCode());
            stateDTO.setStateName(state.getStateName());
            stateDTO.setCountry(countryDTOMapper.countryToDto(state.getCountry()));
            stateDTO.setRegion(state.getRegion());
            stateDTO.setUnionTerritory(state.getIsUnionTerritory());
            stateDTO.setMetaDataTemp(MetaDataMapper.metaDataToTemp(state.getMetaData()));
            stateDTOList.add(stateDTO);
        }
        return stateDTOList;
    }

    @Override
    public List<StateDTO> convertTempStateListToDTOList(List<StateTemp> stateAllList) {
        List<StateDTO> stateDTOList = new ArrayList<>();
        for (StateTemp state : stateAllList) {
            StateDTO stateDTO = new StateDTO();
            stateDTO.setId(state.getId());
            stateDTO.setStateCode(state.getStateCode());
            stateDTO.setStateName(state.getStateName());
            CountryDTO countryDTO = countryService.getCountryByCountryName(state.getCountryName());
            stateDTO.setCountry(countryDTO);
            stateDTO.setRegion(state.getRegion());
            //stateDTO.setUnionTerritory(state.getIsUnionTerritory());
            stateDTO.setMetaDataTemp((state.getMetaDataTemp()));
            stateDTOList.add(stateDTO);
        }
        return stateDTOList;
    }

}
