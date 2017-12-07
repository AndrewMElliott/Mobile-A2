package com.example.comp3074.a2100872220;

import java.io.Serializable;

/**
 * Created by Drew on 12/6/2017.
 */

public class Patient implements Serializable{
    private String firstName;
    private String lastName;
    private Integer id;
    private Integer testID;
    private Integer doctorID;
    private Integer room;

    public Patient(Integer id){

        this.id = id;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestID() {
        return testID;
    }

    public void setTestID(Integer testID) {
        this.testID = testID;
    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }
}
