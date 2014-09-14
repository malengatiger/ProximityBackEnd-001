/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.proximity.dto;

/**
 *
 * @author aubreyM
 */
public class PhotoUploadDTO {

    public static final String BRANCH_PREFIX = "branch";
    public static final String COMPANY_PREFIX = "company";
    public static final String BEACON_PREFIX = "beacon";
   

    public static final int PICTURES_FULL_SIZE = 1;
    public static final int PICTURES_THUMBNAILS = 2;

    private int companyID, branchID, beaconID;

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }
    
   

}
