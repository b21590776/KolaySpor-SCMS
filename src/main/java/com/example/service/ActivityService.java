package com.example.service;

import com.example.model.Activity;

import java.util.List;

public interface ActivityService {
    List<Activity> getAllActivities(String date,String branch);
    Activity findById(int id);
    void deleteActivity(int activityID);
    void saveActivity(Activity activity);
}
