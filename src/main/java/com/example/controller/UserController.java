package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/home")
    public ModelAndView getUserHomePage(HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!getLoggedInUser().getVIPStartTime().isEmpty()){
            String VIPStartTime = getLoggedInUser().getVIPStartTime();
            try {
                Date VIPDate = sdf.parse(VIPStartTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(VIPDate);
                calendar.add(Calendar.DATE,30);
                String output = sdf.format(calendar.getTime());
                System.out.println(output);
                if(sdf.parse(getToday()).compareTo(calendar.getTime()) > 0){
                    userService.makeRegularMember(getLoggedInUser());
                    HttpSession httpSession = request.getSession();
                    httpSession.invalidate();
                    return new ModelAndView("/login","user",new User());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return new ModelAndView("/user/home").addObject("fullname",getLoggedInUser().getFullname());}

    @RequestMapping(value="/user/add-balance",method = RequestMethod.GET)
    public ModelAndView getAddBalancePage(){
        ModelAndView modelAndView = new ModelAndView();
        User user = getLoggedInUser();
        modelAndView.addObject("balance",user.getBalance());
        modelAndView.setViewName("/user/add-balance");
        return modelAndView;
    }

    @PostMapping("/user/add-balance")
    public ModelAndView addBalance(@RequestParam("balance") String balance){
        ModelAndView modelAndView = new ModelAndView();
        User user = getLoggedInUser();
        double tryBalance;
        try { tryBalance = Double.valueOf(balance); }
        catch (Exception e){
            modelAndView.addObject("balance",user.getBalance());
            modelAndView.setViewName("/user/add-balance");
            modelAndView.addObject("message","Please provide valid money format");
            return modelAndView;
        }
        userService.addBalance(user,tryBalance);
        modelAndView.addObject("balance",user.getBalance());
        modelAndView.addObject("message","Transaction successful!");
        modelAndView.setViewName("/user/add-balance");
        return modelAndView;
    }

    @GetMapping("/user/user-account")
    public ModelAndView getUserAccount(){
        if(!getLoggedInUser().getVIPStartTime().isEmpty())
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user/user-account");
            modelAndView.addObject("emptyMessage","");
            modelAndView.addObject("user",getLoggedInUser());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String VIPStartTime = getLoggedInUser().getVIPStartTime();
            try {
                Date VIPDate = sdf.parse(VIPStartTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(VIPDate);
                calendar.add(Calendar.DATE, 30);
                String output = "You are VIP until : " + sdf.format(calendar.getTime());
                modelAndView.addObject("vipMessage",output);
                return modelAndView;
            }catch (Exception e){e.printStackTrace();}
        }
        return new ModelAndView("/user/user-account","user",getLoggedInUser());
    }

    @GetMapping("/user/upgrade")
    public ModelAndView upgrade(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/user-account");
        if(getLoggedInUser().getBalance() < 100){
            modelAndView.addObject("user",getLoggedInUser());
            modelAndView.addObject("message","You don't have enough money to upgrade !");
            return modelAndView;
        }
        else {
            userService.makeVIP(userService.findUserByID(getLoggedInUser().getId()));
            modelAndView.addObject("user",getLoggedInUser());
            modelAndView.addObject("message","You are now VIP !");
            return modelAndView;
        }
    }

    private User getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userService.findUserByEmail(name);
    }

    private String getToday(){
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }
}
