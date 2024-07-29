package org.nucleus.controller;

import org.nucleus.dto.RepaymentPolicyDTO;
import org.nucleus.entity.meta.TempMetaData;
import org.nucleus.service.RepaymentPolicyService;
import org.nucleus.service.RepaymentPolicyTempService;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/maker")
public class RepaymentPolicyMakerController {
    @Autowired
    RepaymentPolicyTempService repaymentPolicyTempService;
    @Autowired
    RepaymentPolicyService repaymentPolicyService;
    @RequestMapping("make-repayment-policy")
    public ModelAndView detailforRepayment()
    {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("repaymentPolicies",mergeList(repaymentPolicyTempService.getAllRepaymentPolicy(),repaymentPolicyService.getAllRepaymentPolicy(),repaymentPolicyTempService.getAllRepaymentPolicyRejeted()));
        modelAndView.setViewName("forward:../views/maker-detailforRepayementPolicy.jsp");
        return modelAndView;
    }
    @RequestMapping("formRepaymentPolicy")
    public ModelAndView showMakerPage()
    {

        ModelAndView modelAndView=new ModelAndView();
        RepaymentPolicyDTO repaymentPolicyDTO=repaymentPolicyTempService.checkSave();
        List<String> buttonValues=new ArrayList<>();
        for(int i=1;i<31;i++)
        {
            buttonValues.add(""+i);
        }
        buttonValues.add("endMonth");
        modelAndView.addObject("buttonValues",buttonValues);
        if(repaymentPolicyDTO!=null) {
            modelAndView.addObject("repaymentPolicy", repaymentPolicyDTO);
        }
        else {
            modelAndView.addObject("repaymentPolicy", new RepaymentPolicyDTO());
        }
        modelAndView.addObject("message","registering");
        modelAndView.setViewName("forward:../views/maker-repaymentpolicy.jsp");
        return modelAndView;
    }


    @RequestMapping("repaymentpolicyinsertion")
    public ModelAndView insertRepayIntoTempTable(@ModelAttribute("repaymentPolicy")@Valid RepaymentPolicyDTO repaymentPolicy , BindingResult error, @RequestParam("submit")String submit)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ModelAndView modelAndView=new ModelAndView();
        System.out.println("1"+repaymentPolicy);
        if(error.hasErrors())
        {

            modelAndView.setViewName("forward:../views/maker-repaymentpolicy.jsp");
            return modelAndView;
        }
        TempMetaData metaData=new TempMetaData();
        try
        {

            if(submit.equalsIgnoreCase("save"))// if the submit is save
            {
                RepaymentPolicyDTO repaymentPolicyDTO=repaymentPolicyTempService.checkSave();
                metaData.setSaveFlag(true);
                repaymentPolicy.setMetaData(metaData);
                if(repaymentPolicyDTO==null)// then check if the previous save in temproray table  if not present then insert directly
                {
                    repaymentPolicyTempService.insertRepaymentPolicy(repaymentPolicy);
                    modelAndView.addObject("message","saved");
                }
                else {// else delete the previous record then insert into the temp table
                    repaymentPolicyTempService.deleteRepaymentPolicy(repaymentPolicyDTO.getRepaymentPolicyCode());
                    repaymentPolicyTempService.insertRepaymentPolicy(repaymentPolicy);
                    modelAndView.addObject("message","saved");
                }

            }
            else if(submit.equalsIgnoreCase("Save & Request Approval"))// if the submit is save and Request Approval
            {
                System.out.println("2"+repaymentPolicy);
                RepaymentPolicyDTO repaymentPolicyDTO=repaymentPolicyTempService.checkSave();
                System.out.println("null   " +repaymentPolicyDTO);
                metaData.setSaveFlag(false);
                System.out.println("3. metadata");
                metaData.setRecordStatus(RecordStatus.N);
                metaData.setCreationDate(Date.valueOf(LocalDate.now()));
                System.out.println(repaymentPolicy.getMetaData());

                System.out.println("4.metadata");
//                    metaData.setCreatedBy(); compulsory
                repaymentPolicy.setMetaData(metaData);
                System.out.println(metaData);
                repaymentPolicy.getMetaData().setCreatedBy(username);
                RepaymentPolicyDTO repaymentPolicyDTO1=repaymentPolicyTempService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode());
                System.out.println("678"+repaymentPolicyDTO1);
                System.out.println("3");
                if(repaymentPolicyDTO1!=null && repaymentPolicyDTO1.getMetaData().getSaveFlag()==true)// present in the temp table as save
                {
                    System.out.println("3"+repaymentPolicy);
                    System.out.println(repaymentPolicyDTO1);
                    repaymentPolicyDTO1.getMetaData().setCreationDate(Date.valueOf(LocalDate.now()));
                    repaymentPolicyDTO1.getMetaData().setRecordStatus(RecordStatus.N);
                    repaymentPolicyDTO1.getMetaData().setSaveFlag(false);
                    repaymentPolicy.getMetaData().setCreatedBy(username);

                    if(repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicyDTO1) )// directly update
                    {
                        System.out.println("save updated ");
                        modelAndView.addObject("message","successfully registered");

                    }
                }
               else if(repaymentPolicyDTO1==null && repaymentPolicyService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode())==null)// if it is neither present in the temp and Master table
                {
                    System.out.println("4"+repaymentPolicy);
                    if(repaymentPolicyTempService.insertRepaymentPolicy(repaymentPolicy) )// directly insert
                    {
                        modelAndView.addObject("message","successfully registered");

                    }

                }
                else if(repaymentPolicyDTO1!=null || repaymentPolicyService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode())!=null) // if it is present in either or both  the table(Master and Temporary) then print policy already present
                {
                    System.out.println("5");
                    modelAndView.addObject("message","policy already existed");


                }


            }

            modelAndView.setViewName("forward:../views/maker-detailforRepayementPolicy.jsp");
            modelAndView.addObject("repaymentPolicies",mergeList(repaymentPolicyTempService.getAllRepaymentPolicy(),repaymentPolicyService.getAllRepaymentPolicy(),repaymentPolicyTempService.getAllRepaymentPolicyRejeted()));
            return modelAndView;
        }
        catch(Exception e)
        {
            System.out.println("6");
            modelAndView.addObject("message",e.getMessage());
            modelAndView.setViewName("forward:../views/maker-detailforRepayementPolicy.jsp");
            modelAndView.addObject("repaymentPolicies",mergeList(repaymentPolicyTempService.getAllRepaymentPolicy(),repaymentPolicyService.getAllRepaymentPolicy(),repaymentPolicyTempService.getAllRepaymentPolicyRejeted()));
            return modelAndView;
        }

    }

    @RequestMapping("editrepaymentpolicytemp/{id}")
    public ModelAndView updateRepayIntoTemptableform(@PathVariable("id") String id)
    {
        ModelAndView modelAndView=new ModelAndView();
        RepaymentPolicyDTO repaymentPolicyDTO= repaymentPolicyService.getRepaymentPolicyById(id);
        if(repaymentPolicyDTO !=null)
        {
            modelAndView.addObject("repaymentPolicy" , repaymentPolicyDTO);
        }
        else {
            modelAndView.addObject("repaymentPolicy" , repaymentPolicyTempService.getRepaymentPolicyById(id));
        }
        List<String> buttonValues=new ArrayList<>();
        for(int i=1;i<31;i++)
        {
            buttonValues.add(""+i);
        }
        buttonValues.add("endMonth");
        modelAndView.addObject("buttonValues",buttonValues);
        modelAndView.setViewName("forward:../../views/maker-updaterepaymentpolicy.jsp");
        return modelAndView;
    }
    @RequestMapping("updaterepaymentpolicy/{status}")
    public ModelAndView updateRepayIntoTempTable( @ModelAttribute("repaymentPolicy")@Valid RepaymentPolicyDTO repaymentPolicy ,BindingResult errors ,@PathVariable("status")RecordStatus recordStatus  , @RequestParam("submit")String submit)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ModelAndView modelAndView=new ModelAndView();
        if(errors.hasErrors())
        {
            System.out.println("error");
            modelAndView.setViewName("forward:../../views/maker-updaterepaymentpolicy.jsp");
            return modelAndView;
        }
        TempMetaData metaData=new TempMetaData();
        try
        {
           if(submit.equalsIgnoreCase("save"))// if the submit is save
           {
               RepaymentPolicyDTO repaymentPolicyDTO=repaymentPolicyTempService.checkSave();
               metaData.setSaveFlag(true);// set Save as true for showing that has not gone for approval
              metaData.setRecordStatus(null);// set status as null as further updation is needed
               repaymentPolicy.setMetaData(metaData);
               if(repaymentPolicyDTO==null)// checked if it is present in the Temp table if not simple insert
               {

                   repaymentPolicyTempService.insertRepaymentPolicy(repaymentPolicy);
                   modelAndView.addObject("message","saved");

               }
               else  {
                   repaymentPolicyTempService.deleteRepaymentPolicy(repaymentPolicyDTO.getRepaymentPolicyCode());
                   repaymentPolicyTempService.insertRepaymentPolicy(repaymentPolicy);
                   modelAndView.addObject("message","saved");


               }
           }
           else if(submit.equalsIgnoreCase("Save & Request Approval")) {// if the submit is Save & Request Approval
               RepaymentPolicyDTO repaymentPolicyDTO=repaymentPolicyTempService.checkSave();

               if(recordStatus==RecordStatus.A)// check if the status is A then it is present in the master table then set Meta Data from Databse
               {
                   repaymentPolicy.setMetaData(repaymentPolicyService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode()).getMetaData());
               }
               else {// If the status is not A then it is present in the temp table then set the meta data from the databse
                   repaymentPolicy.setMetaData(repaymentPolicyTempService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode()).getMetaData());
               }
               if(repaymentPolicy.getMetaData().getRecordStatus()==RecordStatus.N)// if the status is N then directly insert and remain the status as N
               {
                   repaymentPolicy.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                   repaymentPolicy.getMetaData().setModifiedBy(username);
                   //           metaData.setModifiedBy(); compulosry
                   if(repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicy) )
                   {
                       modelAndView.addObject("message","successfully Updated");

                   }
               }
               else if(repaymentPolicy.getMetaData().getRecordStatus()==RecordStatus.A && repaymentPolicyTempService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode())==null)// if it is present in master but not int temp table
               {
                   repaymentPolicy.getMetaData().setRecordStatus(RecordStatus.M);
                   repaymentPolicy.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                   repaymentPolicy.getMetaData().setModifiedBy(username);
                   //           metaData.setModifiedBy(); compulosry
                   repaymentPolicyTempService.insertRepaymentPolicy(repaymentPolicy);// insert the updated value in the temp table
                   modelAndView.addObject("message", "successfully Request for Authorized Record");

               }
               else if(repaymentPolicy.getMetaData().getRecordStatus()==RecordStatus.A && repaymentPolicyTempService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode()).getMetaData().getRecordStatus()==RecordStatus.M )// if it is present in both the table and status is M in the temp table
               {
                   repaymentPolicy.getMetaData().setRecordStatus(RecordStatus.M);
                   repaymentPolicy.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                   repaymentPolicy.getMetaData().setModifiedBy(username);
                   //           metaData.setModifiedBy(); compulosry
                   repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicy);// simply update the value in the temp table
                   modelAndView.addObject("message","successfully Request for Modified Authorized Record");

               }
               else if( repaymentPolicy.getMetaData().getRecordStatus()==RecordStatus.A && (repaymentPolicyTempService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode()).getMetaData().getRecordStatus()==RecordStatus.MR || repaymentPolicyTempService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode()).getMetaData().getRecordStatus()==RecordStatus.DR))//if it is present in both the table and status is MR or DR in the temp table
               {
                   repaymentPolicy.setMetaData(repaymentPolicyService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode()).getMetaData());
                   repaymentPolicy.getMetaData().setRecordStatus(RecordStatus.M);
                   repaymentPolicy.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                   repaymentPolicy.getMetaData().setModifiedBy(username);
                   //           metaData.setModifiedBy(); compulosry
                   repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicy);// simply update
                   modelAndView.addObject("message","successfully Request for Modified Rejected  Authorized Record");

               }
               else if(repaymentPolicy.getMetaData().getRecordStatus()==RecordStatus.M )// simply prenet in temp table with M status
               {
                   repaymentPolicy.setMetaData(repaymentPolicyTempService.getRepaymentPolicyById(repaymentPolicy.getRepaymentPolicyCode()).getMetaData());
                   repaymentPolicy.getMetaData().setRecordStatus(RecordStatus.M);
                   repaymentPolicy.getMetaData().setModifiedDate(Date.valueOf(LocalDate.now()));
                   repaymentPolicy.getMetaData().setModifiedBy(username);
                   //           metaData.setModifiedBy(); compulosry
                   repaymentPolicyTempService.updateRepaymentPolicy(repaymentPolicy);// simply update
                   modelAndView.addObject("message","successfully Request for Modified  Record");

               }

           }
            modelAndView.setViewName("forward:../../views/maker-detailforRepayementPolicy.jsp");
            modelAndView.addObject("repaymentPolicies",mergeList(repaymentPolicyTempService.getAllRepaymentPolicy(),repaymentPolicyService.getAllRepaymentPolicy(),repaymentPolicyTempService.getAllRepaymentPolicyRejeted()));
            return modelAndView;
        }
        catch(Exception e )
        {
            modelAndView.addObject("message ",e.getMessage());
            modelAndView.setViewName("forward:../../views/maker-detailforRepayementPolicy.jsp");
            modelAndView.addObject("repaymentPolicies",mergeList(repaymentPolicyTempService.getAllRepaymentPolicy(),repaymentPolicyService.getAllRepaymentPolicy(),repaymentPolicyTempService.getAllRepaymentPolicyRejeted()));
            return modelAndView;
        }
    }
    @RequestMapping("deleterepaymentpolicytemp/{id}")
    public ModelAndView deleteRepayIntoTempTable(@PathVariable("id") String id)
    {
        ModelAndView modelAndView=new ModelAndView();
        try
        {
            if(repaymentPolicyService.getRepaymentPolicyById(id) ==null)// if not present in the master table
            {
                if(repaymentPolicyTempService.getRepaymentPolicyById(id).getMetaData().getRecordStatus()==RecordStatus.N || repaymentPolicyTempService.getRepaymentPolicyById(id).getMetaData().getRecordStatus()==RecordStatus.NR )// if the status is N or NR in temp table
                {

                    if(repaymentPolicyTempService.deleteRepaymentPolicy(id))// simply delete
                    {
                        modelAndView.addObject("message","successfully deleted");

                    }
                }
            }
            else {// if present in the master table
                if(repaymentPolicyTempService.getRepaymentPolicyById(id)==null)// not present in the temp table
                {
                    RepaymentPolicyDTO repaymentPolicyDTO=repaymentPolicyService.getRepaymentPolicyById(id);
                    repaymentPolicyDTO.getMetaData().setRecordStatus(RecordStatus.D);
                    repaymentPolicyTempService.insertRepaymentPolicy(repaymentPolicyDTO);// simply change status to D and then insert in the temp table
                    modelAndView.addObject("message","successfully deleted Request sent ");

                }
                else// if present in the temp table
                {
                    if(repaymentPolicyTempService.getRepaymentPolicyById(id).getMetaData().getRecordStatus() == RecordStatus.MR ||  repaymentPolicyTempService.getRepaymentPolicyById(id).getMetaData().getRecordStatus() == RecordStatus.M || repaymentPolicyTempService.getRepaymentPolicyById(id).getMetaData().getRecordStatus()==RecordStatus.DR )// if the status is any time of rejected
                    {
                        repaymentPolicyTempService.deleteRepaymentPolicy(id);// simply deleted from the temp table
                        modelAndView.addObject("message","successfully deleted  Modified  Record ");

                    }

                }
            }
        }
        catch (Exception e)
        {
            modelAndView.addObject("message","successfully not deleted");
            modelAndView.setViewName("forward:../../views/maker-detailforRepayementPolicy.jsp");
            modelAndView.addObject("repaymentPolicies",mergeList(repaymentPolicyTempService.getAllRepaymentPolicy(),repaymentPolicyService.getAllRepaymentPolicy(),repaymentPolicyTempService.getAllRepaymentPolicyRejeted()));
            return modelAndView;
        }
        modelAndView.setViewName("forward:../../views/maker-detailforRepayementPolicy.jsp");
        modelAndView.addObject("repaymentPolicies",mergeList(repaymentPolicyTempService.getAllRepaymentPolicy(),repaymentPolicyService.getAllRepaymentPolicy(),repaymentPolicyTempService.getAllRepaymentPolicyRejeted()));
        return modelAndView;
    }

// This method is for showing the list of all temp policy record and all master policy except which are present in the temp table
    private  List<RepaymentPolicyDTO> mergeList(List<RepaymentPolicyDTO> repaymentPolicyDTOListTemp, List<RepaymentPolicyDTO>repaymentPolicyDTOListMaster, List<RepaymentPolicyDTO> repaymentPolicyDTOListRejected)
    {
        Set<String>repaymentCode=new HashSet<>();
        List<RepaymentPolicyDTO> repaymentPolicyDTOList=new ArrayList<>();
        if(repaymentPolicyDTOListTemp!=null)
        {
            for(int i=0;i<repaymentPolicyDTOListTemp.size();i++)
            {
                repaymentCode.add(repaymentPolicyDTOListTemp.get(i).getRepaymentPolicyCode());
                repaymentPolicyDTOList.add(repaymentPolicyDTOListTemp.get(i));
            }
        }
       if(repaymentPolicyDTOListRejected!=null)
       {
           for(int i=0;i<repaymentPolicyDTOListRejected.size();i++)
           {
              repaymentCode.add(repaymentPolicyDTOListRejected.get(i).getRepaymentPolicyCode());
              repaymentPolicyDTOList.add(repaymentPolicyDTOListRejected.get(i));
           }
       }
       if(repaymentPolicyDTOListMaster!=null)
       {
           for(int i=0;i<repaymentPolicyDTOListMaster.size();i++)
           {
               if(!repaymentCode.contains(repaymentPolicyDTOListMaster.get(i).getRepaymentPolicyCode()))
               {
                   repaymentPolicyDTOList.add(repaymentPolicyDTOListMaster.get(i));
               }
           }
       }
        return repaymentPolicyDTOList;
    }

}
