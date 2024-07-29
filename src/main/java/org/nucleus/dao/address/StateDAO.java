package org.nucleus.dao.address;


import org.nucleus.dto.StateDTO;

import java.util.List;

public interface StateDAO {
    boolean saveState(StateDTO stateDTO);
    List<StateDTO> getAllPendingStates();
    StateDTO convertToPermanentEntity(StateDTO stateDTO);
    StateDTO getStateDtoById(Integer id);
    List<StateDTO> getAllStates();
    void editApprovedStateAndRequestApproval(StateDTO stateDTO);

    List<StateDTO> getAllStatesWithApprovedStatus();

    //To populate state dropdown according to a particular country name
    List<StateDTO> getStatesByCountryName(String countryName);

    StateDTO getStateByStateName(String stateName);

    boolean deleteState(Integer id);
    void updateState(StateDTO stateDTO);

}
