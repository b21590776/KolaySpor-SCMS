package com.example.service;

import com.example.model.VipCourse;
import com.example.repository.VipCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VipCourseServiceImpl implements VipCourseService{

    @Autowired
    private VipCourseRepository vipCourseRepository;


    @Override
    public List<VipCourse> getAllVipCourses(String date, String branch) {
        List<VipCourse> vipCourses = new ArrayList<>();
        vipCourseRepository.findVipCourseByDateAndBranchOrderByTime(date,branch).forEach(vipCourses::add);
        return vipCourses;
    }

    @Override
    public VipCourse findById(int id) {
        return vipCourseRepository.findById(id);
    }

    @Override
    public void ReserveVipCourse(int personID, int vipCourseID,String registrarName) {
        VipCourse vipCourse = vipCourseRepository.findById(vipCourseID);
        vipCourse.setRegisteredBy(registrarName);
        vipCourse.setAvailable("No");
        vipCourse.setRegistrarID(personID);
    }

    @Override
    public void DeleteRegistration(int personID, int vipCourseID) {
        VipCourse vipCourse = vipCourseRepository.findById(vipCourseID);
        vipCourse.setRegisteredBy("");
        vipCourse.setAvailable("Yes");
        vipCourse.setRegistrarID(0);
    }

    @Override
    public void deleteVipCourse(int id) {
        vipCourseRepository.deleteVipCourseById(id);
    }

    @Override
    public void saveVipCourse(VipCourse vipCourse) {
        vipCourseRepository.save(vipCourse);
    }
}
