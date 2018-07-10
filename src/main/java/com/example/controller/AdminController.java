package com.example.controller;

import com.example.model.Branch;
import com.example.model.User;
import com.example.service.BranchService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BranchService branchService;

    @GetMapping("/admin/manage-users")
    public ModelAndView getAdminManageUsers(){
        return new ModelAndView("/admin/manage-users","users",userService.findAllUsers());
    }
    @PostMapping("/admin/manage-users")
    public ModelAndView getAdminManageUsersPage(@RequestParam("id") int id, @RequestParam("submitType") String submitType){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/manage-users");
        if(submitType.equals("Make Trainer")){
            userService.makeTrainer(userService.findUserByID(id));
        }
        else if(submitType.equals("Make VIP")){
            userService.makeVIP(userService.findUserByID(id));
        }
        else if(submitType.equals("Make Regular Member")){
            userService.makeRegularMember(userService.findUserByID(id));
        }else if(submitType.equals("Ban")){
            userService.banUser(userService.findUserByID(id));
        }else if(submitType.equals("Make Admin")){
            userService.makeAdmin(userService.findUserByID(id));
        }else if(submitType.equals("Make Manager")){
            userService.makeManager(userService.findUserByID(id));
        }
        modelAndView.addObject("users",userService.findAllUsers());
        return modelAndView;
    }

    @GetMapping("/admin/manage-branches")
    public ModelAndView getManageBranches(){
        return new ModelAndView("/admin/manage-branches","branches",branchService.listAllBranches());
    }
    @PostMapping("/admin/manage-branches")
    public ModelAndView getManageBranchesPage(@RequestParam("id") int id, @RequestParam("submitType") String submitType){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/manage-branches");
        if(submitType.equals("Delete")){
            branchService.deleteBranchByID(id);
        }
        else if(submitType.equals("Change Branch")){
            userService.changeBranch(getLoggedInUser(),branchService.findBranchByID(id).getBranchName());
            modelAndView.addObject("message","Your Branch was changed to : "+branchService.findBranchByID(id).getBranchName()+"!");
        }
        modelAndView.addObject("branches",branchService.listAllBranches());
        return modelAndView;
    }
    @PostMapping("/admin/add-branch")
    public ModelAndView addBranche(@RequestParam("branchName") String branchName){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/manage-branches");
        Branch branch = new Branch();
        if(branchName.isEmpty()){ modelAndView.addObject("message","Invalid Branch Name !"); return modelAndView.addObject("branches",branchService.listAllBranches());}
        branch.setBranchName(branchName);
        branchService.saveBranch(branch);
        modelAndView.addObject("branches",branchService.listAllBranches());
        return modelAndView.addObject("message","New Branch Added Successfully!");
    }
    @GetMapping("/admin/add-branch")
    public String redirectToBranchesPage(){
        return "redirect:/admin/manage-branches";
    }

    private User getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userService.findUserByEmail(name);
    }

}
