package org.nucleus.dao;

import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.permanent.RepaymentPolicy;
import org.nucleus.exception.RepaymentPolicyCodeDuplicationExcaption;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepaymentPolicyDao {
     boolean insertRepaymentPolicy(RepaymentPolicyDTO repaymentPolicy) throws RepaymentPolicyCodeDuplicationExcaption;

     boolean updateRepaymentPolicy(RepaymentPolicyDTO repaymentPolicy);

     boolean deleteRepaymentPolicy(String code);

     RepaymentPolicyDTO getRepaymentPolicyById(String id);

     List<RepaymentPolicyDTO> getAllRepaymentPolicy();

     RepaymentPolicy getRepayPolicyByCode(String code);
}
