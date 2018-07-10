package com.example.service;


import com.example.model.VipCourse;

import java.util.List;

public interface VipCourseService {
    List<VipCourse> getAllVipCourses(String date,String branch);
    VipCourse findById(int id);
    void ReserveVipCourse(int personID,int vipCourseID,String registrarName);
    void DeleteRegistration(int personID,int vipCourseID);
    void deleteVipCourse(int id);
    void saveVipCourse(VipCourse vipCourse);
}
