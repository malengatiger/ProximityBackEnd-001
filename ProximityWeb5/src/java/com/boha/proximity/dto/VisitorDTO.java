/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.dto;

import com.boha.proximity.data.Visitor;
import java.util.List;

/**
 *
 * @author aubreyM
 */
public class VisitorDTO {
    private int visitorID;
    private String firstName, lastName, email;
    private long dateRegistered;
    private List<VisitorTrackDTO> visitorTrackList;
    
    public VisitorDTO(Visitor a) {
        visitorID = a.getVisitorID();
        firstName = a.getFirstName();
        lastName = a.getLastName();
        email = a.getEmail();
        dateRegistered = a.getDateRegistered().getTime();
    }

    public int getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
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

    public long getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(long dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public List<VisitorTrackDTO> getVisitorTrackList() {
        return visitorTrackList;
    }

    public void setVisitorTrackList(List<VisitorTrackDTO> visitorTrackList) {
        this.visitorTrackList = visitorTrackList;
    }
    
}
