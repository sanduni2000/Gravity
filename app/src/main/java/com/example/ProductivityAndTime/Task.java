package com.example.ProductivityAndTime;

public class Task {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String subject;
    private String description;

    public Task() {
    }

    public Task(int year, int month, int day, int hour, int minute, String subject, String description) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.subject = subject;
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public  int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public  int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public  int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public  int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public  String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
