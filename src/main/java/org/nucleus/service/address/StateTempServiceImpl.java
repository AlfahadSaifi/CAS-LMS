package org.nucleus.service.address;

import org.nucleus.dao.address.StateTempDAO;
import org.nucleus.dto.StateDTO;
import org.nucleus.utility.dtomapper.address.StateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional()
public class StateTempServiceImpl implements StateTempService {

    @Autowired
    StateTempDAO stateDao;


    @Autowired
    CountryTempService countryService;

    @Autowired
    private StateMapper stateMapper;


    @Override
    public boolean saveState(StateDTO stateDTO) {
        return stateDao.saveState(stateDTO);
    }

    @Override
    public boolean saveStateAndRequestApproval(StateDTO stateDTO)
    {
        return stateDao.saveState(stateDTO);
    }



    @Override
    public void editStateAndRequestApproval(StateDTO stateDTO)
    {
        stateDao.editStateAndRequestApproval(stateDTO);
    }


    @Override
    public List<StateDTO> getAllStates(){
        return stateDao.getAllStates();
    }
    @Override
    public StateDTO getStateDtoById(Integer id)
    {
        return stateDao.getStateDtoById(id);
    }
    @Override
    public boolean deleteState(Integer id)
    {
      return stateDao.deleteState(id);
    }

    @Override
    public void updateState(StateDTO stateDTO)
    {
        stateDao.updateState(stateDTO);
    }

    @Override
    public boolean doesStateExist(String stateName)
    {
       return stateDao.doesStateExist(stateName);
    }

}
