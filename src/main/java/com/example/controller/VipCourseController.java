package com.example.controller;

import com.example.model.User;
import com.example.model.VipCourse;
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
public class VipCourseController {

    @Autowired
    private UserService userService;

    @Autowired
    private VipCourseService vipCourseService;


    @GetMapping("/vip/vip-courses")
    public ModelAndView getVipCourses(){
        ModelAndView modelAndView = new ModelAndView();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(today,getLoggedInUser().getBranchName()));
        modelAndView.addObject("time",today);
        modelAndView.addObject("minDate",today);
        modelAndView.setViewName("/vip/vip-courses");
        return modelAndView;
    }

    @PostMapping("/vip/vip-courses")
    public ModelAndView getDateBasedVipCourses(@RequestParam("date") String date){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/vip/vip-courses");
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        if(date.isEmpty()) { date = today; modelAndView.addObject("message","Invalid Date!");}
        modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date,getLoggedInUser().getBranchName()));
        modelAndView.addObject("time",date);
        modelAndView.addObject("minDate",today);
        return modelAndView;
    }

    @PostMapping("/vip/manage-courses")
    public ModelAndView manageVIPCourses(@RequestParam("id") int id,@RequestParam("submitType") String submitType,@RequestParam("date") String date){
        ModelAndView modelAndView = new ModelAndView();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        modelAndView.setViewName("/vip/vip-courses");
        modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date,getLoggedInUser().getBranchName()));
        modelAndView.addObject("time",date);
        modelAndView.addObject("minDate",today);
        VipCourse vipCourse;
        vipCourse = vipCourseService.findById(id);
        if(submitType.equals("Register")){
            if(vipCourse.getAvailable().equals("Yes")) {
                vipCourseService.ReserveVipCourse(getLoggedInUser().getId(), id,getLoggedInUser().getFullname());
                modelAndView.addObject("message","Registered Successfully!");
                return modelAndView;
            }
            else {
                modelAndView.addObject("message","This Vip Course is Not Available!");
                return modelAndView;
            }
        }
        if(submitType.equals("Delete")){
            if(vipCourse.getAvailable().equals("Yes")){
                modelAndView.addObject("message","This is Available Course!");
                return modelAndView;
            }
            else if(vipCourse.getRegistrarID() != getLoggedInUser().getId()){
                modelAndView.addObject("message","This Course Not Registered By You"+vipCourse.getRegistrarID());
                return modelAndView;
            }
            else if(vipCourse.getRegistrarID() == getLoggedInUser().getId()){
                vipCourseService.DeleteRegistration(getLoggedInUser().getId(),id);
                modelAndView.addObject("message","Registration Successfully Deleted!");
                return modelAndView;
            }
        }
        modelAndView.addObject("message","Somethings is wrong!");
        return modelAndView;
    }

    @GetMapping("/vip/manage-courses")
    public String redirectToVipCourses(){
        return "redirect:/vip/vip-courses";
    }
    @PostMapping("/trainer/delete-vip-course")
    public ModelAndView deleteVIPCourse(@RequestParam("id") int id,@RequestParam("date") String date){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-vip-courses");
        if(date.isEmpty()) date = getToday();
        modelAndView.addObject("time",date);
        modelAndView.addObject("minDate",getToday());
        VipCourse vipCourse = vipCourseService.findById(id);
        if(getLoggedInUser().getId() != vipCourse.getCreaterID()){
            modelAndView.addObject("message","This VIP Course wasn't created by you !");
            modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date,getLoggedInUser().getBranchName()));
            return modelAndView;
        }
        else {
            vipCourseService.deleteVipCourse(id);
            modelAndView.addObject("message","VIP Course was deleted successfully");
            modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date,getLoggedInUser().getBranchName()));
            return modelAndView;
        }
    }



    @GetMapping("/trainer/manage-vip-courses")
    public ModelAndView getManageVIPCOursePage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-vip-courses");
        modelAndView.addObject("minDate",getToday());
        modelAndView.addObject("time",getToday());
        modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(getToday(),getLoggedInUser().getBranchName()));
        return modelAndView;
    }

    @PostMapping("/trainer/manage-vip-courses")
    public ModelAndView getDateBasedManageVIPCoursePage(@RequestParam("date") String date){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-vip-courses");
        if(date.isEmpty()) { date = getToday(); modelAndView.addObject("message","Invalid Date!"); }
        modelAndView.addObject("minDate",date);
        modelAndView.addObject("time",date);
        modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date,getLoggedInUser().getBranchName()));
        return modelAndView;
    }

    @PostMapping("/trainer/add-vip-course")
    public ModelAndView addVIPCourse(@RequestParam("vipCourseName") String vipCourseName,@RequestParam("date") String date, @RequestParam("hour") String hour){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/trainer/manage-vip-courses");
        modelAndView.addObject("minDate",getToday());
        VipCourse vipCourse = new VipCourse();
        vipCourse.setAvailable("Yes");
        vipCourse.setBranch(getLoggedInUser().getBranchName());
        vipCourse.setCreater(getLoggedInUser().getFullname());
        vipCourse.setCreaterID(getLoggedInUser().getId());
        vipCourse.setVipCourseName(vipCourseName);
        vipCourse.setDate(date);
        vipCourse.setTime(hour);
        if(vipCourseName.isEmpty()){
            modelAndView.addObject("message","Invalid VIP Course Name !");
            modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
            modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
            return modelAndView;
        }
        else if(date.isEmpty()){
            modelAndView.addObject("message","Invalid Date !");
            modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
            modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
            return modelAndView;
        }
        else if(hour.isEmpty()){
            modelAndView.addObject("message","Invalid Time !");
            modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
            modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
            return modelAndView;
        }
        vipCourseService.saveVipCourse(vipCourse);
        modelAndView.addObject("vipCourses",vipCourseService.getAllVipCourses(date.isEmpty() ? getToday() : date,getLoggedInUser().getBranchName()));
        modelAndView.addObject("time",date.isEmpty() ? getToday() : date);
        return modelAndView;
    }


    @GetMapping("/trainer/delete-vip-course")
    public String redirectToManageVIPCourses(){
        return "redirect:/trainer/manage-vip-courses";
    }

    @GetMapping("/trainer/add-vip-course")
    public String redirectToManageVIPCoursesPage(){
        return "redirect:/trainer/manage-vip-courses";
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
