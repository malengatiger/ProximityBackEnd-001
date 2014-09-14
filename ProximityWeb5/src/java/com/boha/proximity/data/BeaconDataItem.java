/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "beaconDataItem")
@NamedQueries({
    @NamedQuery(name = "BeaconDataItem.findByCompany", 
            query = "SELECT b FROM BeaconDataItem b "
                    + "where b.beacon.branch.company.companyID = :id "
                    + "order by b.beacon.branch.branchID"),
    @NamedQuery(name = "BeaconDataItem.findAll", 
            query = "SELECT b FROM BeaconDataItem b order by b.beacon.beaconID"),
    @NamedQuery(name = "BeaconDataItem.findByImageUrl", 
            query = "SELECT b FROM BeaconDataItem b WHERE b.imageUrl = :imageUrl"),
    @NamedQuery(name = "BeaconDataItem.findByText", 
            query = "SELECT b FROM BeaconDataItem b WHERE b.text = :text")})
public class BeaconDataItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int beaconDataItemID;
    @Size(max = 255)
    private String imageUrl;
    @Lob
    @Size(max = 65535)
    private String html;
    @Size(max = 255)
    private String text;
    @JoinColumn(name = "beaconID", referencedColumnName = "beaconID")
    @ManyToOne
    private Beacon beacon;

    public BeaconDataItem() {
    }

    public BeaconDataItem(int beaconDataItemID) {
        this.beaconDataItemID = beaconDataItemID;
    }

    public int getBeaconDataItemID() {
        return beaconDataItemID;
    }

    public void setBeaconDataItemID(int beaconDataItemID) {
        this.beaconDataItemID = beaconDataItemID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }

    
    

    @Override
    public String toString() {
        return "com.boha.proximity.data.BeaconDataItem[ beaconDataItemID=" + beaconDataItemID + " ]";
    }

    public BeaconDataItem(int beaconDataItemID, String imageUrl) {
        this.beaconDataItemID = beaconDataItemID;
        this.imageUrl = imageUrl;
    }

}
