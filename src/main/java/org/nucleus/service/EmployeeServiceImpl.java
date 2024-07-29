package org.nucleus.service;

import org.nucleus.dao.EmployeeDao;
import org.nucleus.entity.permanent.JasperEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeDao employeeDao;
    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }
    @Override
    public List<JasperEntity> getAllEntries() {
        return employeeDao.getAllEntries();
    }

    @Override
    public JasperEntity getEntry(String loanAccountNumber) {
        return employeeDao.getEntry(loanAccountNumber);
    }

    @Override
    public void saveEmployee(JasperEntity jasperEntity) {
        employeeDao.saveEmployee(jasperEntity);
    }
}