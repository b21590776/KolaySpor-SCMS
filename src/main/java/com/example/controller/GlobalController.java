package com.example.controller;

import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GlobalController {

    @RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView getHomePage(){
        return new ModelAndView("home","user",new User());
    }

}
