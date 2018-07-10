package com.example.service;

import com.example.model.Activity;
import com.example.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ActivityServiceImp implements ActivityService{

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> getAllActivities(String date,String branch) {
        List<Activity> result = new ArrayList<>();
        activityRepository.findActivitiesByDateAndBranchOrderByTime(date,branch).forEach(result::add);
        return result;
    }

    @Override
    public Activity findById(int id) {
        return activityRepository.findActivitiesById(id);
    }

    @Override
    public void deleteActivity(int activityID) {
        activityRepository.deleteActivitiesById(activityID);
    }

    @Override
    public void saveActivity(Activity activity) {
        activityRepository.save(activity);
    }
}
