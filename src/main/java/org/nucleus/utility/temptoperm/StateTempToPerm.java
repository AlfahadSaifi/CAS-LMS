package org.nucleus.utility.temptoperm;

import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;

public interface StateTempToPerm{
    State stateTempToStatePerm(StateTemp stateTemp);
}
