package org.nucleus.controller;

import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.service.RepaymentPolicyService;
import org.nucleus.service.RepaymentPolicyTempService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/checker")
public class RepaymentPolicyCheckerController {
    @Autowired
    RepaymentPolicyTempService repaymentPolicyTempService;
    @Autowired
    RepaymentPolicyService repaymentPolicyService;

    @RequestMapping("make-repayment-policy")// redirecting to the checker page for Repayment Policy
    public ModelAndView showCheckerPage() {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(repaymentPolicyTempService.getAllRepaymentPolicy());
        modelAndView.addObject("repaymentPolicies", repaymentPolicyTempService.getAllRepaymentPolicy());
        modelAndView.setViewName("forward:../views/checker-repaymentpolicy.jsp");
        return modelAndView;
    }

    @RequestMapping("approverepaymentpolicies/{id}")
    public ModelAndView approveRepaymentPolicies(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            RepaymentPolicyDTO repaymentPolicyDTO = repaymentPolicyService.getRepaymentPolicyById(id);
            if (repaymentPolicyDTO != null)// checking whether it is present in the master table
            {
                RepaymentPolicyDTO repaymentPolicyDTO1 = repaymentPolicyTempService.getRepaymentPolicyById(id);
                if (repaymentPolicyDTO1.getMetaData().getRecordStatus() == RecordStatus.D) // if present then checked whether status in the temp box is D
                {
                    repaymentPolicyDTO1.getMetaData().setActiveInactiveFlag(false);// Setting it to be inactive so that it cannot be selected for future product

                    repaymentPolicyService.updateRepaymentPolicy(repaymentPolicyDTO1);// updating in the master table
                    repaymentPolicyTempService.deleteRepaymentPolicy(id);// deleting from the temporary table
                    modelAndView.addObject("message", "successfully Deleted Approved");

                }// if the requested table status is other than D means it is M (Record Status)
                repaymentPolicyService.deleteRepaymentPolicy(id);// delete previous record
//                metaData.setAuthorizedBy(); compulosry
                repaymentPolicyDTO1.getMetaData().setAuthorizedBy(username);
                repaymentPolicyDTO1.getMetaData().setRecordStatus(RecordStatus.A);// setting  record status to be A
                repaymentPolicyDTO1.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
                repaymentPolicyService.insertRepaymentPolicy(repaymentPolicyDTO1);// Inserting into Master Table
                repaymentPolicyTempService.deleteRepaymentPolicy(id);// deleting from the temp Table
                modelAndView.addObject("message", "successfully approved");

            } else {// If it is not present in the master table then approve it
                RepaymentPolicyDTO repaymentPolicyDTO1 = repaymentPolicyTempService.getRepaymentPolicyById(id);
//                metaData.setAuthorizedBy(); compulosry
                repaymentPolicyDTO1.getMetaData().setRecordStatus(RecordStatus.A);
                repaymentPolicyDTO1.getMetaData().setAuthorizedBy(username);
                repaymentPolicyDTO1.getMetaData().setAuthorizedDate(Date.valueOf(LocalDate.now()));
                repaymentPolicyService.insertRepaymentPolicy(repaymentPolicyDTO1);// inserting into master table
                repaymentPolicyTempService.deleteRepaymentPolicy(id);// deleting from the temp table
                modelAndView.addObject("message", "successfully approved");


            }
            modelAndView.addObject("repaymentPolicies", repaymentPolicyTempService.getAllRepaymentPolicy());
            modelAndView.setViewName("forward:../../views/checker-repaymentpolicy.jsp");
            return modelAndView;


        } catch (RuntimeException e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("repaymentPolicies", repaymentPolicyTempService.getAllRepaymentPolicy());
            modelAndView.setViewName("forward:../../views/checker-repaymentpolicy.jsp");
            return modelAndView;
        }

    }

    @RequestMapping("rejectedrepaymentpolicytemp/{id}")
    public ModelAndView rejectedRepayIntoTempTable(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        RepaymentPolicyDTO repaymentPolicyDTO = repaymentPolicyTempService.getRepaymentPolicyById(id);

        if (repaymentPolicyService.getRepaymentPolicyById(id) != null) {// checking if it is present in the Master Table

            if (repaymentPolicyDTO.getMetaData().getRecordStatus() == RecordStatus.D) {// If present then If the record Status is D then change the Ststus To DR then Update

                repaymentPolicyDTO.getMetaData().setRecordStatus(RecordStatus.DR);
                if (repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicyDTO)) {
                }
            } else if (repaymentPolicyDTO.getMetaData().getRecordStatus() == RecordStatus.M) {// If present then If the record Status is M then change the Ststus To MR then Update
                repaymentPolicyDTO.getMetaData().setRecordStatus(RecordStatus.MR);
                if (repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicyDTO)) {
                    modelAndView.addObject("message", "successfully Modified rejected");

                }
            }
        }
         else {
            repaymentPolicyDTO.getMetaData().setRecordStatus(RecordStatus.NR);// if it is not present then new So change the satus to NR
            if (repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicyDTO)) {
                modelAndView.addObject("message", "successfully rejected a new Record");

            }
        }

        modelAndView.setViewName("forward:../../views/checker-repaymentpolicy.jsp");
        modelAndView.addObject("repaymentPolicies", repaymentPolicyTempService.getAllRepaymentPolicy());
        return modelAndView;
    }
}
