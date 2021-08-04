package com.example.realmsavedataapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DataModel extends RealmObject {
    @PrimaryKey
    private long id;
    private String coursename;
    private String coursedescrip;
    private String coursetrack;
    private String courseduration;

    public DataModel() {
    }

    public DataModel(String coursename, String coursedescrip, String coursetrack, String courseduration) {
        this.coursename = coursename;
        this.coursedescrip = coursedescrip;
        this.coursetrack = coursetrack;
        this.courseduration = courseduration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoursedescrip() {
        return coursedescrip;
    }

    public void setCoursedescrip(String coursedescrip) {
        this.coursedescrip = coursedescrip;
    }

    public String getCoursetrack() {
        return coursetrack;
    }

    public void setCoursetrack(String coursetrack) {
        this.coursetrack = coursetrack;
    }

    public String getCourseduration() {
        return courseduration;
    }

    public void setCourseduration(String courseduration) {
        this.courseduration = courseduration;
    }
}
