package org.nucleus.service;

import org.nucleus.dao.RepaymentPolicyTempDao;
import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.exception.RepaymentPolicyCodeDuplicationExcaption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RepaymentPolicyTempServiceImpl implements  RepaymentPolicyTempService {
    @Autowired
    RepaymentPolicyTempDao repaymentPolicyDao;

    @Override
    public boolean insertRepaymentPolicy(RepaymentPolicyDTO repaymentPolicyDTO) throws RepaymentPolicyCodeDuplicationExcaption {
       return repaymentPolicyDao.insertRepaymentPolicy(repaymentPolicyDTO);
    }

    @Override
    public boolean updateRepaymentPolicy(RepaymentPolicyDTO repaymentPolicyDTO) {
        return repaymentPolicyDao.updateRepaymentPolicy(repaymentPolicyDTO);
    }

    @Override
    public boolean deleteRepaymentPolicy(String code) {
        return repaymentPolicyDao.deleteRepaymentPolicy(code);
    }

    @Override
    public RepaymentPolicyDTO getRepaymentPolicyById(String id) {
        System.out.println("service By Id");
        return repaymentPolicyDao.getRepaymentPolicyById(id);
    }

    @Override
    public List<RepaymentPolicyDTO> getAllRepaymentPolicy() {
       return repaymentPolicyDao.getAllRepaymentPolicy();
    }

    @Override
    public RepaymentPolicyDTO checkSave() {
        System.out.println("Service...");
        return repaymentPolicyDao.checkSave();
    }

    @Override
    public List<RepaymentPolicyDTO> getAllRepaymentPolicyRejeted() {
        return repaymentPolicyDao.getAllRepaymentPolicyRejeted();
    }

}