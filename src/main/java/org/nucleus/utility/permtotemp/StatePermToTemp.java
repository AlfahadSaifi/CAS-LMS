package org.nucleus.utility.permtotemp;

import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;

public interface StatePermToTemp {
    StateTemp statePermToStateTemp(State state);
}
