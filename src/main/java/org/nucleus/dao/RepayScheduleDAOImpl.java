package org.nucleus.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.entity.permanent.RepaySchedule;
import org.nucleus.utility.dtomapper.RepayScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepayScheduleDAOImpl implements RepayScheduleDAO{

    SessionFactory sessionFactory;

    @Autowired
    RepayScheduleDAOImpl(SessionFactory factory){this.sessionFactory=factory;}

    @Override
    public List<RepayScheduleDTO> fetchRepaySchedule(Long loanId) {
        List<RepaySchedule> repayScheduleList=null;
        List<RepayScheduleDTO> repayScheduleDTOList= new ArrayList<>();
        Session session= sessionFactory.getCurrentSession();
        try{
            repayScheduleList = session.createQuery("from RepaySchedule where loanId = :loanId", RepaySchedule.class)
                    .setParameter("loanId", loanId)
                    .getResultList();
        }catch (Exception e){
            return repayScheduleDTOList;
        }

        for(RepaySchedule repaySchedule:repayScheduleList){
            repayScheduleDTOList.add(RepayScheduleMapper.convertToDTO(repaySchedule));
        }
        return repayScheduleDTOList;
    }


}
