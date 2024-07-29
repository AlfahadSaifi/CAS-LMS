package org.nucleus.controller;

import org.nucleus.dto.EligibilityPolicyParameterDefinitionDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.CheckerEligibilityPolicyParameterDefinitionService;
import org.nucleus.service.MakerEligibilityPolicyParameterDefinitionService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

@Controller
@PreAuthorize("hasRole('MAKER')")
@RequestMapping("/maker")
public class MakerEligibilityPolicyParameterDefinitionController {



    @Autowired
    private CheckerEligibilityPolicyParameterDefinitionService checkerEligibilityParameterService;

    @Autowired
    private MakerEligibilityPolicyParameterDefinitionService makerEligibilityParameterService;



    @RequestMapping("/eligibility-parameter") //do must be removed main jsp
    public ModelAndView mainForm(@ModelAttribute("message") String message){
        ModelAndView modelAndView = new ModelAndView();
        List<EligibilityPolicyParameterDefinitionDTO> perParameters = checkerEligibilityParameterService.getAllEligibilityParameter();

        List<EligibilityPolicyParameterDefinitionDTO> tempParameters = makerEligibilityParameterService.getAllTempEligibilityParameter();

        if(tempParameters != null) {
            Iterator<EligibilityPolicyParameterDefinitionDTO> itr = perParameters.iterator();
            while (itr.hasNext()) {
                EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = itr.next();
                if (tempParameters.contains(eligibilityParameterDTO)  ) {
                    itr.remove();
                }

            }
        }
        modelAndView.addObject("tempParameters", tempParameters);
        modelAndView.addObject("perParameters", perParameters);
        modelAndView.addObject("message", message);
        modelAndView.setViewName("maker-eligibility-policy-parameter-definition");
        return modelAndView;
    }



    @GetMapping("/new-paramater")
    public ModelAndView parameterForm(

    ){
        ModelAndView modelAndView=new ModelAndView();
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO= makerEligibilityParameterService.getSavedParameter();

        if(eligibilityParameterDTO !=null){

            modelAndView.addObject("eligibilityParameterTemp",eligibilityParameterDTO);
            modelAndView.setViewName("eligibility-policy-parameter-definition-update-form");
            return modelAndView;
        }
        else{
            modelAndView.addObject("eligibilityParameter",new EligibilityPolicyParameterDefinitionDTO());
        }
        modelAndView.setViewName("eligibility-policy-parameter-definition-form");
        return modelAndView;
    }




    @PostMapping("/save-paramater")
    public ModelAndView parameterFormSubmit(
            @ModelAttribute("eligibilityParameter")  @Valid EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO,
            BindingResult errors
            ,@RequestParam("save") String submit
            , RedirectAttributes redirectAttributes
    )
    {

        ModelAndView modelAndView = new ModelAndView();
        TempMetaData tempMetaData = new TempMetaData();
        if (errors.hasErrors()) {
            modelAndView.setViewName("eligibility-policy-parameter-definition-form");
            return modelAndView;
        }

        if (makerEligibilityParameterService.searchEligibilityParameterPerByCode(eligibilityParameterDTO.getEligibilityParameterCode()) != null
                || checkerEligibilityParameterService.searchEligibilityParameterPerByCode(eligibilityParameterDTO.getEligibilityParameterCode()) != null
        ) {
            modelAndView.addObject("message", "parameter already exist use different parameter code");
            modelAndView.addObject("eligibilityParameter", eligibilityParameterDTO);
            modelAndView.setViewName("eligibility-policy-parameter-definition-form");
            return modelAndView;
        }

        if (submit.equalsIgnoreCase("Save")) {
            //          setting meta data
            tempMetaData.setSaveFlag(true);
            eligibilityParameterDTO.setTempMetaData(tempMetaData);
            if(makerEligibilityParameterService.saveTempEligibilityParameter(eligibilityParameterDTO)){
                redirectAttributes.addFlashAttribute("message", "parameter added successfully");
            }
        } else {
            tempMetaData.setRecordStatus(RecordStatus.N);
            tempMetaData.setSaveFlag(false);
            eligibilityParameterDTO.setTempMetaData(tempMetaData);
            if(makerEligibilityParameterService.insertTempEligibilityParameter(eligibilityParameterDTO)){
                redirectAttributes.addFlashAttribute("message", "parameter inserted successfully");
            }

        }

        modelAndView.setViewName("redirect:/maker/eligibility-parameter");
        return modelAndView;

    }




    @RequestMapping ("/delete/{id}")
    public ModelAndView deleteItemById(@PathVariable("id") Long id
            ,RedirectAttributes redirectAttributes
    ) {
        makerEligibilityParameterService.deleteEligibilityParameterById(id);
        ModelAndView modelAndView=new ModelAndView();
        redirectAttributes.addFlashAttribute("message","parameter deleted");
        modelAndView.setViewName("redirect:/maker/eligibility-parameter");
        return modelAndView;
    }

    @RequestMapping ("/delete/per{id}")
    public ModelAndView deletePerById(@PathVariable("id") Long id
            ,RedirectAttributes redirectAttributes
    ) {
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = checkerEligibilityParameterService.getEligibilityParameterById(id);

        eligibilityParameterDTO.getTempMetaData().setRecordStatus(RecordStatus.D);
        makerEligibilityParameterService.insertTempEligibilityParameter(eligibilityParameterDTO);
        ModelAndView modelAndView=new ModelAndView();
        redirectAttributes.addFlashAttribute("message","parameter deleted");
        modelAndView.setViewName("redirect:/maker/eligibility-parameter");
        return modelAndView;
    }


    @RequestMapping ("update/{id}")
    public String updateItemById(@PathVariable("id") Long id
            ,Model model
    ) {

        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = makerEligibilityParameterService.getTempEligibilityParameterById(id);
        model.addAttribute("eligibilityParameterTemp",eligibilityParameterDTO);
        return "../views/eligibility-policy-parameter-definition-update-form";
    }

    @PostMapping("update-paramater")
    public ModelAndView parameterUpdateFormSubmit(
            @ModelAttribute("eligibilityParameterTemp") EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO
            , RedirectAttributes redirectAttributes
    ){
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO1 = makerEligibilityParameterService.getTempEligibilityParameterById(eligibilityParameterDTO.getEligibilityParameterId());
        eligibilityParameterDTO.setTempMetaData(eligibilityParameterDTO1.getTempMetaData());


        makerEligibilityParameterService.updateTempEligibilityParameter(eligibilityParameterDTO);
        ModelAndView modelAndView=new ModelAndView();
        redirectAttributes.addFlashAttribute("message","parameter updated");

        modelAndView.setViewName("redirect:/maker/eligibility-parameter");
        return modelAndView;
    }



    @RequestMapping ("update/per{id}")
    public String update(@PathVariable("id") Long id
            ,Model model
    ) {
        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO = checkerEligibilityParameterService.getEligibilityParameterById(id);

        model.addAttribute("eligibilityParameterPer",eligibilityParameterDTO);
        return "../views/per-eligibility-policy-parameter-definition-update-form";
    }


    @PostMapping("update-paramater-per")
    public ModelAndView parameterUpdateForm(
            @ModelAttribute("eligibilityParameterTemp") EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO
            ,RedirectAttributes redirectAttributes
    ){



        EligibilityPolicyParameterDefinitionDTO eligibilityParameterDTO1 = checkerEligibilityParameterService.getEligibilityParameterById(eligibilityParameterDTO.getEligibilityParameterId());
        eligibilityParameterDTO.setTempMetaData(eligibilityParameterDTO1.getTempMetaData());
        makerEligibilityParameterService.insertTempEligibilityParameter(eligibilityParameterDTO);

        ModelAndView modelAndView=new ModelAndView();
        redirectAttributes.addFlashAttribute("message","parameter updated");

        modelAndView.setViewName("redirect:/maker/eligibility-parameter");
        return modelAndView;
    }

}
