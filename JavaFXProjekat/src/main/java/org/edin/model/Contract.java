package org.edin.model;

import java.io.Serializable;

public class Contract implements Serializable {

    private String firstName;
    private String lastName;
    private String address;
    private int speed;
    private String bandwidth;
    private String duration;

    public Contract(){

    }

    public Contract(String firstName, String lastName, String address, Integer speed, String bandwidth, String duration){
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.speed = speed;
    this.bandwidth = bandwidth;
    this.duration = duration;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
