package org.nucleus.utility.permtotemp;

import org.nucleus.entity.permanent.Country;
import org.nucleus.entity.temporary.CountryTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryPermToTempImpl implements CountryPermToTemp {
    @Autowired
    private StatePermToTemp statePermToTemp;

    @Override
    public CountryTemp permToTemp(Country country) {
        CountryTemp countryTemp = new CountryTemp();
        countryTemp.setId(country.getId());
        countryTemp.setCountryGroup(country.getCountryGroup());
        countryTemp.setCountryName(country.getCountryName());
        countryTemp.setCountryIsoCode(country.getCountryIsoCode());
        countryTemp.setCountryIsdCode(country.getCountryIsdCode());
        countryTemp.setNationality(country.getNationality());
        countryTemp.setRegion(country.getRegion());
        countryTemp.setStatus(country.getStatus());

        /*List<StateTemp> stateTemps = new ArrayList<>();
        List<State> states = country.getStates();
        for(State s : states){
            stateTemps.add(statePermToTemp.statePermToStateTemp(s));
        }
        countryTemp.setStates(stateTemps);*/


        return countryTemp;
    }
}
