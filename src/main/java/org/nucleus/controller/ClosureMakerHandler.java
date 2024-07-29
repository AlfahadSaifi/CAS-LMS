package org.nucleus.controller;
import org.nucleus.service.LoanClosureServiceTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@PreAuthorize("hasRole('MAKER')")
@RequestMapping("/maker")
public class ClosureMakerHandler {
    @Autowired
    LoanClosureServiceTemp temp;
    @RequestMapping("regular-closure")
    public ModelAndView referJsp(){
        ModelAndView modelAndView=new ModelAndView();
        if(temp.performLoanClosure()) {
            modelAndView.addObject("loanClosureTempList", temp.getAllRegularClosureData());
            modelAndView.addObject("loanNotClosedList", temp.getLoansNotClosed());
            modelAndView.setViewName("closure-maker-datatable");
        }
        else {
            modelAndView.setViewName("closure-perform-errorPage");
        }
        return modelAndView;
    }
}