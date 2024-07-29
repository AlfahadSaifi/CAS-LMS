package org.nucleus.dao.address;

import org.nucleus.dto.StateDTO;

import java.util.List;

public interface StateTempDAO {
    boolean saveState(StateDTO state);
    /*boolean saveStateAndRequestApproval(StateDTO stateDTO);*/
    void editStateAndRequestApproval(StateDTO stateDTO);

    List<StateDTO> getAllStates();

    boolean deleteState(Integer id);
    void updateState(StateDTO stateDTO);
    StateDTO getStateDtoById(Integer id);
    boolean doesStateExist(String stateName);
}
