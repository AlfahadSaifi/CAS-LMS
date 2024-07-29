package org.nucleus.utility.permtotemp;

import org.nucleus.entity.permanent.City;
import org.nucleus.entity.temporary.CityTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityPermToTempImpl implements CityPermToTemp{
    @Autowired
    private StatePermToTemp statePermToTemp;

    @Override
    public CityTemp permToTemp(City city) {
        CityTemp cityTemp = new CityTemp();
        cityTemp.setCityCode(city.getCityCode());
        cityTemp.setCityName(city.getCityName());
        cityTemp.setCityMICRCode(city.getCityMICRCode());
        cityTemp.setStdCode(city.getStdCode());
        cityTemp.setCityCode(city.getCityCode());
        /*cityTemp.setState(statePermToTemp.statePermToStateTemp(city.getState()));*/
        return cityTemp;

    }
}
