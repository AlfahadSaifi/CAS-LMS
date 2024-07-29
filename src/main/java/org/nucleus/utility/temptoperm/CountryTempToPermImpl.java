package org.nucleus.utility.temptoperm;

import org.nucleus.entity.permanent.Country;
import org.nucleus.entity.temporary.CountryTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryTempToPermImpl implements CountryTempToPerm {
    @Autowired
    private StateTempToPerm stateTempToPerm;
    @Override
    public Country tempToPerm(CountryTemp countryTemp) {

        Country country = new Country();

        country.setId(countryTemp.getId());
        country.setCountryGroup(countryTemp.getCountryGroup());
        country.setCountryName(countryTemp.getCountryName());
        country.setStatus(countryTemp.getStatus());
        country.setRegion(countryTemp.getRegion());
        country.setNationality(countryTemp.getNationality());
        country.setCountryIsoCode(countryTemp.getCountryIsoCode());
        country.setCountryIsdCode(countryTemp.getCountryIsdCode());

        /*List<State> states = new ArrayList<>();
        List<StateTemp> stateTemps = countryTemp.getStates();

        for(StateTemp stateTemp: stateTemps){
            states.add(stateTempToPerm.stateTempToStatePerm(stateTemp));
        }*/
        /*country.setStates(states);
*/


        return country;


    }
}
