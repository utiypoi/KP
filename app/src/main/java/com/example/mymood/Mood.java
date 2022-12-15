package com.example.mymood;

public class Mood {
    private int id;
    private String nameMood;
    private String comment;
    private String dateTimeEntry;
    private int priority;
    public Mood(int id, String nameMood, String comment, String dateTimeEntry, int priority){
        this.id = id;
        this.nameMood = nameMood;
        this.comment = comment;
        this.dateTimeEntry = dateTimeEntry;
        this.priority = priority;
    }
    public int getId(){
        return id;
    }
    public String getNameMood(){
        return nameMood;
    }
    public String getComment(){
        return comment;
    }
    public String getDateTimeEntry(){
        return dateTimeEntry;
    }
    public int getPriority(){
        return priority;
    }
}
