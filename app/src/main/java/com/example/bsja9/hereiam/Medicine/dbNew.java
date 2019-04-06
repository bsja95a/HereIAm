package com.example.bsja9.hereiam.Medicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

public class dbNew implements Serializable {

   private int id;
   private String name;
   private String dose;
   private String timesPerDay;
   private String comments;

   public dbNew(){

   }

   public dbNew(String name, String dose, String timesPerDay, String comments){
       this(-1,name,dose,timesPerDay,comments);
   }

    public dbNew(int id, String name, String dose, String timesPerDay, String comments) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.timesPerDay = timesPerDay;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(String timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
