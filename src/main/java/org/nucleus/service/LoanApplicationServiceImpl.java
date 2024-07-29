package org.nucleus.service;

import org.nucleus.dao.LoanApplicationDAO;
import org.nucleus.dao.LoanApplicationTempDAO;
import org.nucleus.dto.LoanApplicationDTO;
import org.nucleus.entity.permanent.LoanApplication;
import org.nucleus.entity.temporary.LoanApplicationTemp;
import org.nucleus.utility.enums.LoanStatus;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoanApplicationServiceImpl implements LoanApplicationService {
    private final LoanApplicationDAO loanApplicationPermanentDAO;

    private final LoanApplicationTempDAO loanApplicationTemporaryDAO;
    @Autowired
    public LoanApplicationServiceImpl(LoanApplicationDAO loanApplicationPermanentDAO, LoanApplicationTempDAO loanApplicationTemporaryDAO) {
        this.loanApplicationPermanentDAO = loanApplicationPermanentDAO;
        this.loanApplicationTemporaryDAO = loanApplicationTemporaryDAO;
    }

    @Override
    public LoanApplication getApplicationByAccountNumber(String loanAccountNumber){
        return loanApplicationPermanentDAO.getApplicationByAccountNumber(loanAccountNumber);
    }
    @Override
    public Long insertPermanent(LoanApplicationDTO loanApplicationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // set AuthorizedBy and AuthorizedDate
        loanApplicationDTO.setAuthorizedBy(authentication.getName());
        loanApplicationDTO.setAuthorizedDate(Date.valueOf(LocalDate.now()));

        return loanApplicationPermanentDAO.create(loanApplicationDTO) ;
    }

    @Override
    public LoanApplicationDTO readPermanent(Long loanId) {
        if(loanId==null)return null;

        return loanApplicationPermanentDAO.read(loanId);
    }

    public LoanApplicationDTO readPermanentByLoanApplicationId(String loanApplicationId) {
        return loanApplicationPermanentDAO.readByLoanApplicationId(loanApplicationId);
    }

    @Override
    public LoanApplicationDTO readPermanentByLoanAccountNumber(String loanAccountNumber) {
        return loanApplicationPermanentDAO.readByLoanAccountNumber(loanAccountNumber);
    }


    // delete loan application from permanent - make delete request
    public boolean deleteRequestPermanent(LoanApplicationDTO loanApplicationDTO){
        if(loanApplicationDTO==null)return false;

        // set status D
        loanApplicationDTO.setRecordStatus(RecordStatus.D);

        // so insert into temporary table with delete request
        return loanApplicationTemporaryDAO.create(loanApplicationDTO)!=null;
    }

    @Override
    public List<LoanApplicationDTO> readingAllPermanent() {
        return loanApplicationPermanentDAO.readingAll();
    }


    /*Temporary Methods*/

    @Override
    public Long createTemporary(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO==null)return null;


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // set created by and created date
        loanApplicationDTO.setCreatedBy(authentication.getName());
        loanApplicationDTO.setCreationDate(Date.valueOf(LocalDate.now()));
        loanApplicationDTO.setRecordStatus(RecordStatus.N);

        return loanApplicationTemporaryDAO.create(loanApplicationDTO);
    }

    @Override
    public LoanApplicationDTO readTemporary(Long loanId) {
        if(loanId==null)return null;

        return loanApplicationTemporaryDAO.read(loanId);
    }

    public LoanApplicationDTO readTemporaryByLoanApplicationId(String loanApplicationId) {
        return loanApplicationTemporaryDAO.readByLoanApplicationId(loanApplicationId);
    }

    @Override
    public boolean updateTemporary(LoanApplicationDTO loanApplicationDTO) {
        if(loanApplicationDTO==null)return false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(loanApplicationDTO.getRecordStatus()==RecordStatus.A || loanApplicationDTO.getRecordStatus()==RecordStatus.MR){
            loanApplicationDTO.setRecordStatus(RecordStatus.M);
        }
        else if(loanApplicationDTO.getRecordStatus()==RecordStatus.N || loanApplicationDTO.getRecordStatus()==RecordStatus.NR){
            loanApplicationDTO.setRecordStatus(RecordStatus.N);
        }

        // set modified date and modified  by
        loanApplicationDTO.setModifiedDate(Date.valueOf(LocalDate.now()));
        loanApplicationDTO.setModifiedBy(authentication.getName());

        if(loanApplicationDTO.getRecordStatus()==RecordStatus.A){
            return loanApplicationTemporaryDAO.create(loanApplicationDTO)!=null;
        }

        return loanApplicationTemporaryDAO.merge(loanApplicationDTO);
    }

    @Override
    public List<LoanApplicationDTO> readingAllTemporary() {
        return loanApplicationTemporaryDAO.readingAll();
    }

    // delete from temporary - hard delete
    @Override
    public boolean deleteTemporary(String loanApplicationId) {
        if(loanApplicationId==null)return false;
        return loanApplicationTemporaryDAO.delete(loanApplicationId);
    }


    public List<LoanApplicationDTO> readAll(){
        List<LoanApplicationDTO> loanApplicationTemporaryDTOS = readingAllTemporary();
        List<LoanApplicationDTO> loanApplicationPermanentDTOS = readingAllPermanent();

        Set<String> loanApplicationsTempSet = new HashSet<>();
        for(LoanApplicationDTO loanApplication: loanApplicationTemporaryDTOS){
            loanApplicationsTempSet.add(loanApplication.getLoanApplicationId());
        }

        // remove all loan applications from permanent which is already exist in temp
        loanApplicationPermanentDTOS.removeIf(loanApplicationDTO -> loanApplicationsTempSet.contains(loanApplicationDTO.getLoanApplicationId()));

        List<LoanApplicationDTO> loanApplicationDTOS = new ArrayList<>();
        loanApplicationDTOS.addAll(loanApplicationPermanentDTOS);
        loanApplicationDTOS.addAll(loanApplicationTemporaryDTOS);
        return loanApplicationDTOS;
    }

    public boolean delete(String loanApplicationId){
        LoanApplicationDTO loanApplicationTemporaryDTO = readTemporaryByLoanApplicationId(loanApplicationId);

        if(loanApplicationTemporaryDTO!=null){
            return deleteTemporary(loanApplicationId); // hard delete from temporary
        }

        // if exist in permanent then made delete request
        LoanApplicationDTO loanApplicationDTO = readPermanentByLoanApplicationId(loanApplicationId);

        return deleteRequestPermanent(loanApplicationDTO);
    }

    @Override
    public boolean approveLoanApplication(String loanApplicationId) {

        // delete from temporary
        LoanApplicationDTO loanApplicationDTO = readTemporaryByLoanApplicationId(loanApplicationId);
        if(loanApplicationDTO==null)return false;

        // if record status is D then hard delete from both table
        if(loanApplicationDTO.getRecordStatus()==RecordStatus.D){
            loanApplicationTemporaryDAO.delete(loanApplicationId);

            return loanApplicationPermanentDAO.delete(loanApplicationId);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        loanApplicationDTO.setAuthorizedBy(authentication.getName());

        // if record status is M then hard delete from temp table and update in permanent table
        if(loanApplicationDTO.getRecordStatus()==RecordStatus.M){
            loanApplicationTemporaryDAO.delete(loanApplicationId);

            // set record A
            loanApplicationDTO.setRecordStatus(RecordStatus.A);

            // update in permanent table
            return loanApplicationPermanentDAO.merge(loanApplicationDTO);
        }

        loanApplicationTemporaryDAO.delete(loanApplicationId);

        // set record A
        loanApplicationDTO.setRecordStatus(RecordStatus.A);
        loanApplicationDTO.setLoanStatus(LoanStatus.APPROVED);
        loanApplicationDTO.setAuthorizedDate(Date.valueOf(LocalDate.now()));
        if(loanApplicationDTO.getLoanAccountNumber()==null){
            loanApplicationDTO.setLoanAccountNumber(generateLoanAccountNumber());
        }

        // insert into permanent
        return loanApplicationPermanentDAO.create(loanApplicationDTO)!=null;
    }

    @Override
    public boolean rejectLoanApplication(String loanApplicationId) {
        // delete from temporary
        LoanApplicationDTO loanApplicationDTO = readTemporaryByLoanApplicationId(loanApplicationId);

        // if record is modified
        if(loanApplicationDTO.getRecordStatus()==RecordStatus.M){
            loanApplicationDTO.setRecordStatus(RecordStatus.MR);
        }

        // if record is N
        else if(loanApplicationDTO.getRecordStatus()==RecordStatus.N){
            loanApplicationDTO.setRecordStatus(RecordStatus.NR);
        }

        else if (loanApplicationDTO.getRecordStatus()==RecordStatus.D) {
            loanApplicationDTO.setRecordStatus(RecordStatus.DR);
        }

        // if loan is not approved before
        if(LoanStatus.APPROVED!=loanApplicationDTO.getLoanStatus())
            loanApplicationDTO.setLoanStatus(LoanStatus.REJECTED);

        loanApplicationDTO.setModifiedDate(Date.valueOf(LocalDate.now()));

        return loanApplicationTemporaryDAO.merge(loanApplicationDTO);
    }

    @Override
    public List<LoanApplicationDTO> getAllNotRejected() {
        return readingAllTemporary().stream()
                .filter(loanApplicationDTO -> loanApplicationDTO.getRecordStatus()!=RecordStatus.MR &&
                        loanApplicationDTO.getRecordStatus()!= RecordStatus.DR &&
                        loanApplicationDTO.getRecordStatus()!= RecordStatus.NR
                )
                .collect(Collectors.toList());
    }

    @Override
    public LoanApplicationDTO readAny(String loanApplicationId) {
        LoanApplicationDTO loanApplicationDTO = readPermanentByLoanApplicationId(loanApplicationId);

        return loanApplicationDTO!=null ? loanApplicationDTO : readTemporaryByLoanApplicationId(loanApplicationId);
    }

    @Override
    public String generateLoanAccountNumber() {
        Long sequenceNumber=loanApplicationTemporaryDAO.getNextSequenceNumber();
        StringBuilder sequence = new StringBuilder(String.valueOf(sequenceNumber));

        while (sequence.length() < 9) {
            sequence.insert(0, "0");
        }
        return "ACC" + sequence;
    }


    // generate unique loan application
    @Override
    public String generateLoanApplicationId() {
        Long sequenceNumber=loanApplicationTemporaryDAO.getNextSequenceNumber();
        StringBuilder sequence = new StringBuilder(String.valueOf(sequenceNumber));

        while (sequence.length() < 9) {
            sequence.insert(0, "0");
        }
        return "APP" + sequence;
    }
    @Override
    public LoanApplicationTemp getLoanApplicationAgainstLoanId(Long loanId) {
        return loanApplicationPermanentDAO.getLoanApplicationAgainstLoanId(loanId);
    }
    @Override
    public LoanApplication getLoanIdAgainstApplication(String loanApplicationId) {
        return loanApplicationPermanentDAO.getLoanIdAgainstApplication(loanApplicationId);
    }

    @Override
    public boolean updatePermanent(LoanApplication loanApplication) {
        return loanApplicationPermanentDAO.merge(loanApplication);
    }
}
