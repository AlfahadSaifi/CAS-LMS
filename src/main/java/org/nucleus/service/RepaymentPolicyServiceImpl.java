package org.nucleus.service;

import org.nucleus.dao.RepaymentPolicyDao;
import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.permanent.RepaymentPolicy;
import org.nucleus.exception.RepaymentPolicyCodeDuplicationExcaption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RepaymentPolicyServiceImpl  implements  RepaymentPolicyService{
    @Autowired
    RepaymentPolicyDao repaymentPolicyDao;

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

        return repaymentPolicyDao.getRepaymentPolicyById(id) ;
    }

    @Override
    public List<RepaymentPolicyDTO> getAllRepaymentPolicy() {
        return repaymentPolicyDao.getAllRepaymentPolicy();
    }

    @Override
    public RepaymentPolicy getRepayPolicyByCode(String code) {
        return repaymentPolicyDao.getRepayPolicyByCode(code);
    }
}
