/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.dto;

import com.boha.proximity.data.Beacon;
import com.boha.proximity.data.Visitor;
import com.boha.proximity.data.VisitorTrack;

/**
 *
 * @author aubreyM
 */
public class VisitorTrackDTO {
    private int visitorTrackID, visitorID, beaconID;
    private String beaconName;
    private long dateTracked;
    private String firstName, lastName, email;
    
    public VisitorTrackDTO(VisitorTrack a) {
        visitorID = a.getVisitor().getVisitorID();
        visitorTrackID = a.getVisitorTrackID();
        Beacon b = a.getBeacon();
        beaconID = b.getBeaconID();
        beaconName = b.getBeaconName();
        dateTracked = a.getDateTracked().getTime();
    }
    public VisitorTrackDTO(VisitorTrack a, boolean withNames) {
        Visitor v = a.getVisitor();
        visitorID = v.getVisitorID();
        firstName = v.getFirstName();
        lastName = v.getLastName();
        email = v.getEmail();
        visitorTrackID = a.getVisitorTrackID();
        Beacon b = a.getBeacon();
        beaconID = b.getBeaconID();
        beaconName = b.getBeaconName();
        dateTracked = a.getDateTracked().getTime();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVisitorTrackID() {
        return visitorTrackID;
    }

    public void setVisitorTrackID(int visitorTrackID) {
        this.visitorTrackID = visitorTrackID;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public long getDateTracked() {
        return dateTracked;
    }

    public void setDateTracked(long dateTracked) {
        this.dateTracked = dateTracked;
    }
    
    
}
