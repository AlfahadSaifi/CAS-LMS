package org.nucleus.controller;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.service.LoanClosureServiceTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@PreAuthorize("hasRole('CHECKER')")
//@RequestMapping("/checker")
public class ClosureCheckerController {
    private static final Logger logger= LogManager.getLogger(ClosureCheckerController.class);
    @Autowired
    LoanClosureServiceTemp temp;
    @RequestMapping("regular-closure")
    public ModelAndView referJsp(){
        ModelAndView modelAndView=new ModelAndView();
        temp.performLoanClosure();
        modelAndView.addObject("loanClosureTempList", temp.getAllRegularClosureData());
        modelAndView.addObject("loanNotClosedList", temp.getLoansNotClosed());
        modelAndView.setViewName("closure-maker-datatable");
        return modelAndView;
    }

}
