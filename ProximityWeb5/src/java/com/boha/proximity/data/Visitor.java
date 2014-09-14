/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "visitor")
@NamedQueries({
    @NamedQuery(name = "Visitor.findAll", query = "SELECT v FROM Visitor v order by v.lastName, v.firstName"),
    @NamedQuery(name = "Visitor.findByVisitorID", query = "SELECT v FROM Visitor v WHERE v.visitorID = :visitorID"),
    @NamedQuery(name = "Visitor.findByFirstName", query = "SELECT v FROM Visitor v WHERE v.firstName = :firstName"),
    @NamedQuery(name = "Visitor.findByLastName", query = "SELECT v FROM Visitor v WHERE v.lastName = :lastName"),
    @NamedQuery(name = "Visitor.findByEmail", query = "SELECT v FROM Visitor v WHERE v.email = :email"),
    @NamedQuery(name = "Visitor.findByDateRegistered", query = "SELECT v FROM Visitor v WHERE v.dateRegistered = :dateRegistered")})
public class Visitor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "visitorID")
    private Integer visitorID;
    @Size(max = 100)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 100)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateRegistered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "visitor")
    private List<VisitorTrack> visitorTrackList;

    public Visitor() {
    }

    public Visitor(Integer visitorID) {
        this.visitorID = visitorID;
    }

    public Visitor(Integer visitorID, String email, Date dateRegistered) {
        this.visitorID = visitorID;
        this.email = email;
        this.dateRegistered = dateRegistered;
    }

    public Integer getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(Integer visitorID) {
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

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public List<VisitorTrack> getVisitorTrackList() {
        return visitorTrackList;
    }

    public void setVisitorTrackList(List<VisitorTrack> visitorTrackList) {
        this.visitorTrackList = visitorTrackList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitorID != null ? visitorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visitor)) {
            return false;
        }
        Visitor other = (Visitor) object;
        if ((this.visitorID == null && other.visitorID != null) || (this.visitorID != null && !this.visitorID.equals(other.visitorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.proximity.data.Visitor[ visitorID=" + visitorID + " ]";
    }
    
}
