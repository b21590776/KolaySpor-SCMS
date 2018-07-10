package com.example.repository;

import com.example.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findActivitiesByDateAndBranchOrderByTime(String date,String branch);
    Activity findActivitiesById(int id);
    void deleteActivitiesById(int id);
}
