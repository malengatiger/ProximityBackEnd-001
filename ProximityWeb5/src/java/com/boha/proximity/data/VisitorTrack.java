/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "visitorTrack")
@NamedQueries({
    @NamedQuery(name = "VisitorTrack.findAll", query = "SELECT v FROM VisitorTrack v order by v.dateTracked desc"),
    @NamedQuery(name = "VisitorTrack.findVisitorBeaconTrack", 
            query = "SELECT v FROM VisitorTrack v order by v.beacon.beaconName, v.dateTracked desc"),
    @NamedQuery(name = "VisitorTrack.findByDateTracked", 
            query = "SELECT v FROM VisitorTrack v WHERE v.dateTracked = :dateTracked")})
public class VisitorTrack implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "visitorTrackID")
    private Integer visitorTrackID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateTracked")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTracked;
    @JoinColumn(name = "visitorID", referencedColumnName = "visitorID")
    @ManyToOne(optional = false)
    private Visitor visitor;
    @JoinColumn(name = "beaconID", referencedColumnName = "beaconID")
    @ManyToOne(optional = false)
    private Beacon beacon;

    public VisitorTrack() {
    }

    public VisitorTrack(Integer visitorTrackID) {
        this.visitorTrackID = visitorTrackID;
    }

    public VisitorTrack(Integer visitorTrackID, Date dateTracked) {
        this.visitorTrackID = visitorTrackID;
        this.dateTracked = dateTracked;
    }

    public Integer getVisitorTrackID() {
        return visitorTrackID;
    }

    public void setVisitorTrackID(Integer visitorTrackID) {
        this.visitorTrackID = visitorTrackID;
    }

    public Date getDateTracked() {
        return dateTracked;
    }

    public void setDateTracked(Date dateTracked) {
        this.dateTracked = dateTracked;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }

  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitorTrackID != null ? visitorTrackID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitorTrack)) {
            return false;
        }
        VisitorTrack other = (VisitorTrack) object;
        if ((this.visitorTrackID == null && other.visitorTrackID != null) || (this.visitorTrackID != null && !this.visitorTrackID.equals(other.visitorTrackID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.proximity.data.VisitorTrack[ visitorTrackID=" + visitorTrackID + " ]";
    }
    
}
