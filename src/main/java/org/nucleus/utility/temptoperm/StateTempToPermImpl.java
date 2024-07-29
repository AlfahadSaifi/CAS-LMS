package org.nucleus.utility.temptoperm;

import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class
StateTempToPermImpl implements StateTempToPerm{
    @Autowired private CityTempToPerm cityTempToPerm;

    @Override
    public State stateTempToStatePerm(StateTemp stateTemp) {
        State state =new State();
        state.setId(stateTemp.getId());
        state.setStateCode(stateTemp.getStateCode());
        state.setStateName(stateTemp.getStateName());
        state.setRegion(stateTemp.getRegion());
        //state.setIsUnionTerritory(stateTemp.getIsUnionTerritory());

        return state;
    }
}