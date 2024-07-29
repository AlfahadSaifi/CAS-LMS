package org.nucleus.utility.temptoperm;

import org.nucleus.entity.permanent.Country;
import org.nucleus.entity.temporary.CountryTemp;

public interface CountryTempToPerm {
    Country tempToPerm(CountryTemp countryTemp);
}
