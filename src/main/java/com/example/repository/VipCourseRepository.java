package com.example.repository;

import com.example.model.VipCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VipCourseRepository extends JpaRepository<VipCourse, Long> {
    List<VipCourse> findVipCourseByDateAndBranchOrderByTime(String date,String branch);
    VipCourse findById(int id);
    void deleteVipCourseById(int id);
}
