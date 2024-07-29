package org.nucleus.utility.permtotemp;

import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class
StatePermToTempImpl implements StatePermToTemp{
    @Autowired
    private CityPermToTemp cityPermToTemp;

    @Override
    public StateTemp statePermToStateTemp(State state) {

        StateTemp stateTemp =new StateTemp();
        stateTemp.setId(state.getId());
        stateTemp.setStateCode(state.getStateCode());
        stateTemp.setStateName(state.getStateName());
        stateTemp.setRegion(state.getRegion());
        //stateTemp.setIsUnionTerritory(state.getIsUnionTerritory());


        return stateTemp;
    }
}
