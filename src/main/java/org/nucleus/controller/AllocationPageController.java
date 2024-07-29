package org.nucleus.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@PreAuthorize("hasRole('CHECKER')")
@RequestMapping("checker")
public class AllocationPageController {

    @RequestMapping("/allocation")
    public String mapToAllocationPage(){
        return "allocation-page";
    }
}
