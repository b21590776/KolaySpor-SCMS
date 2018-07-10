package com.example.controller;

import com.example.model.Activity;
import com.example.model.User;
import com.example.service.ActivityService;
import com.example.service.BranchService;
import com.example.service.UserService;
import com.example.service.VipCourseService;
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
public class ActivityController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/user/activities")
    public ModelAndView getCourses(){
        ModelAndView modelAndView = new ModelAndView();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        modelAndView.addObject("activities",activityService.getAllActivities(today,getLoggedInUser().getBranchName()));
        modelAndView.addObject("time",today);
        modelAndView.addObject("minDate",today);
        modelAndView.setViewName("/user/activities");
        return modelAndView;
    }

    @PostMapping("/user/activities")
    public ModelAndView getDateBasedCourses(@RequestParam("date") String date){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/activities");
        String today = getToday();
        if(date.isEmpty()) { date = today; modelAndView.addObject("message","Invalid Date");}
        modelAndView.addObject("activities",activityService.getAllActivities(date,getLoggedInUser().getBranchName()));
        modelAndView.addObject("time",date);
        modelAndView.addObject("minDate",today);
        return modelAndView;
    }

    @GetMapping("/trainer/manage-activities")
    public ModelAndView getManageActivitiesPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-activities");
        modelAndView.addObject("minDate",getToday());
        modelAndView.addObject("time",getToday());
        modelAndView.addObject("activities",activityService.getAllActivities(getToday(),getLoggedInUser().getBranchName()));
        return modelAndView;
    }
    @PostMapping("/trainer/manage-activities")
    public ModelAndView getManageActivitiesBasedDate(@RequestParam("date") String date){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-activities");
        if(date.isEmpty()) {date = getToday();modelAndView.addObject("message","Invalid Date");}
        modelAndView.addObject("time",date);
        modelAndView.addObject("minDate",getToday());
        modelAndView.addObject("activities",activityService.getAllActivities(date,getLoggedInUser().getBranchName()));
        return modelAndView;
    }

    @PostMapping("/trainer/delete-activities")
    public ModelAndView editActivities(@RequestParam("id") int id, @RequestParam("date") String date){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-activities");
        if(date.isEmpty()) date = getToday();
        modelAndView.addObject("time",date);
        modelAndView.addObject("minDate",getToday());
        Activity activity = activityService.findById(id);
        if(getLoggedInUser().getId() != activity.getCreaterID()) {
            modelAndView.addObject("message", "This Activity wasn't created by you!");
            modelAndView.addObject("activities",activityService.getAllActivities(date,getLoggedInUser().getBranchName()));
            return modelAndView;
        }
        else {
            activityService.deleteActivity(id);
            modelAndView.addObject("message","Activity was deleted successfully!");
            modelAndView.addObject("activities",activityService.getAllActivities(date,getLoggedInUser().getBranchName()));
            return modelAndView;
        }
    }

    @GetMapping("/trainer/delete-activities")
    public String redirectToManageActivities(){
        return "redirect:/trainer/manage-activities";
    }

    @PostMapping("/trainer/add-activity")
    public ModelAndView addActivity(@RequestParam("activityName") String activityName,@RequestParam("date") String date, @RequestParam("hour") String hour){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-activities");
        modelAndView.addObject("minDate",getToday());
        Activity activity = new Activity();
        activity.setActivity_name(activityName);
        activity.setCreater(getLoggedInUser().getFullname());
        activity.setCreaterID(getLoggedInUser().getId());
        activity.setBranch(getLoggedInUser().getBranchName());
        activity.setDate(date);
        activity.setTime(hour);
        if(activityName.isEmpty()){
            modelAndView.addObject("message","Invalid Activity Name!");
            modelAndView.addObject("activities",activityService.getAllActivities(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
            modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
            return modelAndView;
        }
        else if(date.isEmpty()){
            modelAndView.addObject("message","Invalid Date");
            modelAndView.addObject("activities",activityService.getAllActivities(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
            modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
            return modelAndView;
        }
        else if(hour.isEmpty()){
            modelAndView.addObject("message","Invalid Time");
            modelAndView.addObject("activities",activityService.getAllActivities(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
            modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
            return modelAndView;
        }
        activityService.saveActivity(activity);
        modelAndView.addObject("activities",activityService.getAllActivities(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
        modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
        return modelAndView;
    }

    @GetMapping("/trainer/add-activity")
    public String redirectToManageActivitiesTrainer(){
        return "redirect:/trainer/manage-activities";
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
