package org.nucleus.utility.permtotemp;

import org.nucleus.entity.permanent.Country;
import org.nucleus.entity.temporary.CountryTemp;

public interface CountryPermToTemp {
    CountryTemp permToTemp(Country country);
}
