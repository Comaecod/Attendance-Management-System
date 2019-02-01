package com.example.vishnu.typroject;

public class Timetable {
    String dateOf,timeslotLecture,timeslotPractical,course,subject;

    public Timetable(){

    }

    public Timetable(String dateOf, String timeslotLecture, String timeslotPractical, String course, String subject) {
        this.dateOf = dateOf;
        this.timeslotLecture = timeslotLecture;
        this.timeslotPractical = timeslotPractical;
        this.course = course;
        this.subject = subject;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = dateOf;
    }

    public void setTimeslotLecture(String timeslotLecture) {
        this.timeslotLecture = timeslotLecture;
    }

    public void setTimeslotPractical(String timeslotPractical) {
        this.timeslotPractical = timeslotPractical;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDateOf() {
        return dateOf;
    }

    public String getTimeslotLecture() {
        return timeslotLecture;
    }

    public String getTimeslotPractical() {
        return timeslotPractical;
    }

    public String getCourse() {
        return course;
    }

    public String getSubject() {
        return subject;
    }
}
