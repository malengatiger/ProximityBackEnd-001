/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.dto;

import com.boha.proximity.data.Beacon;
import com.boha.proximity.util.FileUtility;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aubreyM
 */
public class BeaconDTO {

    private int beaconID;
    private String macAddress;
    private String proximityUUID;
    private int major;
    private int minor;
    private int branchID, companyID;
    private String beaconName, companyName, branchName;
    private List<BeaconDataItemDTO> beaconDataItemList;
    private List<String> imageFileNameList;
    
    public BeaconDTO(Beacon a) {
        beaconID = a.getBeaconID();
        macAddress = a.getMacAddress();
        proximityUUID = a.getProximityUUID();
        major = a.getMajor();
        minor = a.getMinor();
        branchID = a.getBranch().getBranchID();
        companyID = a.getBranch().getCompany().getCompanyID();
        beaconName = a.getBeaconName();
        branchName = a.getBranch().getBranchName();
        companyName = a.getBranch().getCompany().getCompanyName();
        try {
            imageFileNameList = FileUtility.getBeaconImageFiles(a.getBranch().getCompany().getCompanyID(), branchID, beaconID);
        } catch (Exception ex) {
            Logger.getLogger(BeaconDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public List<String> getImageFileNameList() {
        return imageFileNameList;
    }

    public void setImageFileNameList(List<String> imageFileNameList) {
        this.imageFileNameList = imageFileNameList;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
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

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public List<BeaconDataItemDTO> getBeaconDataItemList() {
        return beaconDataItemList;
    }

    public void setBeaconDataItemList(List<BeaconDataItemDTO> beaconDataItemList) {
        this.beaconDataItemList = beaconDataItemList;
    }
    
}
