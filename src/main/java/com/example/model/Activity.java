package com.example.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private int id;

    @Column(name = "activity_name")
    @NotEmpty
    private String activity_name;

    @Column(name = "creater")
    @NotEmpty
    private String creater;

    @Column(name = "createrID")
    private int createrID;

    @Column(name = "branch")
    @NotEmpty
    private String branch;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    @NotNull
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
}

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCreaterID() {
        return createrID;
    }

    public void setCreaterID(int createrID) {
        this.createrID = createrID;
    }
}
