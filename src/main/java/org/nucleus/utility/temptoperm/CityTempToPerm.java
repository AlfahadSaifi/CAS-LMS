package org.nucleus.utility.temptoperm;

import org.nucleus.entity.permanent.City;
import org.nucleus.entity.temporary.CityTemp;

public interface CityTempToPerm {
    City tempToPerm(CityTemp cityTemp);
}
