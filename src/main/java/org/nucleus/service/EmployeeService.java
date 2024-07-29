package org.nucleus.service;


import org.nucleus.entity.permanent.JasperEntity;

import java.util.List;

public interface EmployeeService {
    List<JasperEntity> getAllEntries();

    JasperEntity getEntry(String loanAccountNumber);

    void saveEmployee(JasperEntity jasperEntity);
}