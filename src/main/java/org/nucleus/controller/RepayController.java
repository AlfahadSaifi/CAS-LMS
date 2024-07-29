package org.nucleus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("{xxxx}/repay-schedule")
public class RepayController {

    @GetMapping
    public String showRepayPage(){
        return "repay-schedule";
    }

//    @RequestMapping("repay-schedule-details")
//    public String showHomePage(){
//        return "landing-repay-schedule";
//    }
}
