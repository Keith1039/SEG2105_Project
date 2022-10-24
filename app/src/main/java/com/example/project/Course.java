package com.example.project;

public class Course {
    private String code;
    private String name;

    private Instructor prof;
    private String description;
    private int capacity;
    private int[] days;
    private int[] hours;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instructor getProf() {
        return prof;
    }

    public void setProf(Instructor prof) {
        this.prof = prof;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int[] getDays() {
        return days;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public int[] getHours() {
        return hours;
    }

    public void setHours(int[] hours) {
        this.hours = hours;
    }
}
