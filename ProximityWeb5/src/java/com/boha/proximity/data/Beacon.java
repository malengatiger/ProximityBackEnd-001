/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "beacon")
@NamedQueries({
    @NamedQuery(name = "Beacon.findAll",
            query = "SELECT b FROM Beacon b order by b.branch.branchID"),
    @NamedQuery(name = "Beacon.findByBranch",
            query = "SELECT b FROM Beacon b where b.branch.branchID = :id"),
    @NamedQuery(name = "Beacon.deleteByBranch",
            query = "DELETE FROM Beacon b where b.branch.branchID = :id"),
    @NamedQuery(name = "Beacon.deleteByID",
            query = "DELETE FROM Beacon b where b.beaconID = :id"),
    @NamedQuery(name = "Beacon.findByMacAddress",
            query = "SELECT b FROM Beacon b "
            + "WHERE b.macAddress = :macAddress"),
    @NamedQuery(name = "Beacon.findByCompany",
            query = "SELECT b FROM Beacon b "
            + "where b.branch.company.companyID = :id "
            + "order by b.branch.branchID"),

    @NamedQuery(name = "Beacon.findByProximityUUID",
            query = "SELECT b FROM Beacon b "
            + "WHERE b.proximityUUID = :proximityUUID"),})
public class Beacon implements Serializable {
    @Column(name = "major")
    private Integer major;
    @Column(name = "minor")
    private Integer minor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "beacon")
    private List<VisitorTrack> visitorTrackList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "beaconID")
    private int beaconID;
    @Size(max = 100)
    @Column(name = "macAddress")
    private String macAddress;
    @Size(max = 255)
    @Column(name = "proximityUUID")
    private String proximityUUID;
    @Size(max = 255)
    @Column(name = "beaconName")
    private String beaconName;
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "branchID", referencedColumnName = "branchID")
    @ManyToOne(optional = false)
    private Branch branch;
    @OneToMany(mappedBy = "beacon")
    private List<BeaconDataItem> beaconDataItemList;

    public Beacon() {
    }

    public Beacon(int beaconID) {
        this.beaconID = beaconID;
    }

    public Beacon(int beaconID, String macAddress, String proximityUUID, int major, int minor) {
        this.beaconID = beaconID;
        this.macAddress = macAddress;
        this.proximityUUID = proximityUUID;
        this.major = major;
        this.minor = minor;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getProximityUUID() {
        return proximityUUID;
    }

    public void setProximityUUID(String proximityUUID) {
        this.proximityUUID = proximityUUID;
    }


    public List<BeaconDataItem> getBeaconDataItemList() {
        return beaconDataItemList;
    }

    public void setBeaconDataItemList(List<BeaconDataItem> beaconDataItemList) {
        this.beaconDataItemList = beaconDataItemList;
    }

    @Override
    public String toString() {
        return "com.boha.proximity.data.Beacon[ beaconID=" + beaconID + " ]";
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public List<VisitorTrack> getVisitorTrackList() {
        return visitorTrackList;
    }

    public void setVisitorTrackList(List<VisitorTrack> visitorTrackList) {
        this.visitorTrackList = visitorTrackList;
    }

}
