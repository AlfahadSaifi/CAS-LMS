package org.nucleus.dao;



import org.nucleus.entity.permanent.JasperEntity;

import java.util.List;

public interface EmployeeDao {
     List<JasperEntity> getAllEntries();
     JasperEntity getEntry(String loanAccountNumber);
     void saveEmployee(JasperEntity jasperEntity);
}
