package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManagerController {


    @Autowired
    private UserService userService;

    @GetMapping("/manager/manage-users")
    public ModelAndView getManagerManageUsers(){
        return new ModelAndView("/manager/manage-users","users",userService.findUsersByBranchName(getLoggedInUser().getBranchName()));
    }

    @PostMapping("/manager/manage-users")
    public ModelAndView getManagerManageUsersPage(@RequestParam("id") int id, @RequestParam("submitType") String submitType ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/manage-users");
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
        }
        modelAndView.addObject("users",userService.findUsersByBranchName(getLoggedInUser().getBranchName()));
        return modelAndView;
    }


    private User getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userService.findUserByEmail(name);
    }

}
