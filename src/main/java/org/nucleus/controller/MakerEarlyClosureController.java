package org.nucleus.controller;

import org.nucleus.dto.LoanAccountDTO;
import org.nucleus.dto.LoanClosureDTO;
import org.nucleus.dto.MakerEarlyClosureDTO;
import org.nucleus.service.EarlyClosureService;
import org.nucleus.utility.enums.ClosureStatus;
import org.nucleus.utility.enums.LoanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@PreAuthorize("hasRole('MAKER')")
@RequestMapping("/maker")
public class MakerEarlyClosureController
{
    private final EarlyClosureService earlyClosureService;

    @Autowired
    public MakerEarlyClosureController(EarlyClosureService earlyClosureService)
    {
        this.earlyClosureService = earlyClosureService;
    }

    @RequestMapping("/early-closure")
    public ModelAndView formOpening(@ModelAttribute MakerEarlyClosureDTO makerEarlyClosureDTO)
    {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.addObject("errorMessage"," ");
        modelAndView.addObject("earlyClosureResponseDTO", makerEarlyClosureDTO);
        modelAndView.setViewName("early-closure-maker-dash");
        return modelAndView;
    }

    @RequestMapping("/closure-form")
    public ModelAndView closureForm(@ModelAttribute MakerEarlyClosureDTO makerEarlyClosureDTO,
                                    @RequestParam("action")String action,
                                    @RequestParam("loanAccountSearch") String loanAccountNumber
    )
    {
        ModelAndView modelAndView=new ModelAndView();
        if("search".equalsIgnoreCase(action))
        {
            // Values fetched from customer and loan account entity using dao of respective class
            LoanAccountDTO loanAccountDTO=earlyClosureService.getLoanAccount(loanAccountNumber);
            final String ERROR_MESSAGE = "errorMessage";
            if(loanAccountDTO==null)
            {
                modelAndView.addObject(ERROR_MESSAGE,"No such account found!");
            }
            else if (loanAccountDTO.getLoanStatus()== LoanStatus.CLOSED)
            {
                modelAndView.addObject(ERROR_MESSAGE,"Loan already Closed!");
            }
            else if (earlyClosureService.getLoanClosureByAccountNumber(loanAccountNumber)!=null)
            {
            modelAndView.addObject(ERROR_MESSAGE,"Request already sent for this particular Loan Account!");
            }
            else
            {
                try
                {
                    makerEarlyClosureDTO = earlyClosureService.getAllMakerFields(loanAccountNumber);
                    modelAndView.addObject("earlyClosureResponseDTO", makerEarlyClosureDTO);
                }
                catch (NullPointerException e)
                {
                    modelAndView.addObject(ERROR_MESSAGE,"All required Fields are not available!");
                }
            }
            modelAndView.setViewName("early-closure-maker-dash");
        }
        else if("submit".equalsIgnoreCase(action))
        {
            //Data Shown On the page to customer is saved in the loan closure table
            /*LoanClosureDTO loanClosureDTO=new LoanClosureDTO();
            loanClosureDTO.setClosureStatus(ClosureStatus.EARLY_CLOSURE);
            LoanAccountDTO loanAccountDTO= earlyClosureService.getLoanAccount(loanAccountNumber);
            loanClosureDTO.setLoanAccountDTO(loanAccountDTO);
            loanClosureDTO.setLoanClosureDate(makerEarlyClosureDTO.getClosureDate());
            earlyClosureService.createLoanClosure(loanClosureDTO);
            modelAndView.setViewName("request-success");*/

            LoanClosureDTO loanClosureDTO=new LoanClosureDTO();
            loanClosureDTO.setClosureStatus(ClosureStatus.EARLY_CLOSURE);
            loanClosureDTO.setLoanClosureDate(makerEarlyClosureDTO.getClosureDate());
            loanClosureDTO.setLoanAccountDTO(earlyClosureService.getLoanAccount(makerEarlyClosureDTO.getLoanAccountNumber()));
            System.out.println("Creating early loan closure 1");
            Long loanClosureId = earlyClosureService.createLoanClosureTemp(loanClosureDTO);
            modelAndView.addObject("loanClosureId",loanClosureId);
            modelAndView.addObject("dueAmount",makerEarlyClosureDTO.getTotalClosureAmount());
            modelAndView.setViewName("request-success");
        }
        return modelAndView;
    }
}
