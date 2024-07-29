package org.nucleus.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("{xxxx}/reschedule")
@PreAuthorize("hasRole('MAKER') or hasRole('CHECKER')")
public class RescheduleController {
    private static final String RESCHEDULE_PAGE = "reschedule";
    @GetMapping
    public String showReschedulePage(){
        return RESCHEDULE_PAGE;
    }
}
