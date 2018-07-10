package com.example.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class VipCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "vipCourseName")
    private String vipCourseName;

    @Column(name = "branch")
    private String branch;

    @Column(name = "creater")
    @NotEmpty
    private String creater;

    @Column(name = "createrID")
    private int createrID;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    @NotNull
    private String time;

    @Column(name = "available")
    private String available = "Yes";

    @Column(name = "registeredBy")
    private String registeredBy;

    @Column(name = "registrarID")
    private int registrarID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVipCourseName() {
        return vipCourseName;
    }

    public void setVipCourseName(String vipCourseName) {
        this.vipCourseName = vipCourseName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public int getRegistrarID() {
        return registrarID;
    }

    public void setRegistrarID(int registrarID) {
        this.registrarID = registrarID;
    }

    public int getCreaterID() {
        return createrID;
    }

    public void setCreaterID(int createrID) {
        this.createrID = createrID;
    }
}
