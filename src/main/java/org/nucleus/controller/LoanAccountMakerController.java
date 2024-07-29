/*
 *Author: Rakesh Kumar
 *
 */

package org.nucleus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("maker")
public class LoanAccountMakerController {
    @GetMapping("/loan-account")
    public String accessDenied(){
        return "access-denied";
    }
}
