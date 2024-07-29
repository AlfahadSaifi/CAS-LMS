package org.nucleus.service.address;

import org.nucleus.dao.address.StateDAO;
import org.nucleus.dto.StateDTO;
import org.nucleus.entity.permanent.State;
import org.nucleus.entity.temporary.StateTemp;
import org.nucleus.utility.dtomapper.address.StateMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StateServiceImpl implements StateService{

    private StateDAO stateDAO;


    private StateTempService stateTempService;

    private StateMapper stateMapper;



    @Autowired
    public StateServiceImpl(StateDAO stateDAO, StateTempService stateTempService, StateMapper stateMapper){
        this.stateDAO=stateDAO;
        this.stateMapper = stateMapper;
        this.stateTempService = stateTempService;
    }
    @Override
    public boolean saveState(StateDTO stateDTO){
        return stateDAO.saveState(stateDTO);
    }

    @Override
    public List<StateDTO> getAllPendingStates() {
        return stateDAO.getAllPendingStates();
    }
    @Override
    public StateDTO convertToPermanentEntity(StateDTO stateDTO)
    {
        return stateDAO.convertToPermanentEntity(stateDTO);
    }
    @Override
    public List<StateDTO> getAllStates() {
        return stateDAO.getAllStates();
    }
    @Override
    public StateDTO getStateDtoById(Integer id) {
        return stateDAO.getStateDtoById(id);
    }
    @Override
    public void editApprovedStateAndRequestApproval(StateDTO stateDTO) {
        editApprovedStateAndRequestApproval(stateDTO);
    }
    @Override
    public List<StateDTO> getAllStatesWithApprovedStatus(){
        return stateDAO.getAllStatesWithApprovedStatus();
    }
    @Override
    public List<StateDTO> getStatesByCountryName(String countryName){
        return stateDAO.getStatesByCountryName(countryName);
    }
    @Override
    public StateDTO getStateByStateName(String stateName){
        return stateDAO.getStateByStateName(stateName);
    }
    @Override
    public boolean deleteState(Integer id){
        return  stateDAO.deleteState(id);
    }


    @Override
    public void updateState(StateDTO stateDTO)
    {
     stateDAO.updateState(stateDTO);
    }
    @Override
    public boolean approveState(StateDTO stateDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();


        stateDTO.getMetaDataTemp().setAuthorizedBy(username);
        stateDTO.getMetaDataTemp().setAuthorizedDate(Date.valueOf(LocalDate.now()));



        if(stateDTO != null && RecordStatus.D.equals(stateDTO.getMetaDataTemp().getRecordStatus()))
        {
            stateTempService.deleteState(stateDTO.getId());
            State state = stateMapper.stateDtoToStatePermanent(getStateByStateName(stateDTO.getStateName()));
            stateDAO.deleteState(state.getId());
            return true;
        }
        else if(RecordStatus.N.equals(stateDTO.getMetaDataTemp().getRecordStatus())) {
            if (stateDTO != null && stateDAO.saveState(stateDTO)) {
                return stateTempService.deleteState(stateDTO.getId());
            }
        }
        else
        {
            if(stateDTO != null)
            {

                stateDTO =stateDAO.getStateByStateName(stateDTO.getStateName());
                stateDAO.updateState(stateDTO);
                return stateTempService.deleteState(stateDTO.getId());
            }
        }
        return false;
    }


}



