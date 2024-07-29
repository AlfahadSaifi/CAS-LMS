package org.nucleus.service.address;

import org.nucleus.dto.StateDTO;

import java.util.List;

public interface StateTempService {
    boolean saveState(StateDTO stateDto);

    boolean saveStateAndRequestApproval(StateDTO stateDto);

    void editStateAndRequestApproval(StateDTO stateDTO);

    List<StateDTO> getAllStates();

    StateDTO getStateDtoById(Integer id);
    boolean deleteState(Integer id);
    void updateState(StateDTO stateDTO);

    boolean doesStateExist(String stateName);

}
