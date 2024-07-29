package org.nucleus.service.address;


import org.nucleus.dto.StateDTO;

import java.util.List;

public interface StateService {
    boolean saveState(StateDTO stateDTO);

    List<StateDTO> getAllPendingStates();
    StateDTO convertToPermanentEntity(StateDTO stateDTO);
    List<StateDTO> getAllStates();
    StateDTO getStateDtoById(Integer id);
    void editApprovedStateAndRequestApproval(StateDTO stateDTO);

    List<StateDTO> getAllStatesWithApprovedStatus();

    List<StateDTO> getStatesByCountryName(String countryName);
    StateDTO getStateByStateName(String stateName);
    boolean deleteState(Integer id);


    void updateState(StateDTO stateDTO);
    boolean approveState(StateDTO stateDTO);
}
