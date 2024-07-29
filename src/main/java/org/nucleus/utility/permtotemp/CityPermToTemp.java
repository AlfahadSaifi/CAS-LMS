package org.nucleus.utility.permtotemp;

import org.nucleus.entity.permanent.City;
import org.nucleus.entity.temporary.CityTemp;

public interface CityPermToTemp {
    CityTemp permToTemp(City city);
}
