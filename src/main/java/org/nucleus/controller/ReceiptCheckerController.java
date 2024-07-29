package org.nucleus.controller;


import org.nucleus.dto.ReceiptDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.ReceiptService;
import org.nucleus.utility.OldDateEditor;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/checker")
@PreAuthorize("hasRole('CHECKER')")
public class ReceiptCheckerController {
    @Autowired
    ReceiptService receiptService;


    @GetMapping("receipt")
    public String showReceiptForm(Model model) {  // show jsp form to search receivablePayable-id
        model.addAttribute("receiptChecker", new ReceiptDTO());
        return "forward:../views/receipt-checker-final.jsp";
    }

    @InitBinder
    public void bind(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, "toDate", new OldDateEditor());
        binder.registerCustomEditor(Date.class,"fromDate",new OldDateEditor());
    }

}
