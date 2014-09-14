/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.dto;

import com.boha.proximity.data.Branch;
import java.util.List;

/**
 *
 * @author aubreyM
 */
public class BranchDTO {
   private int branchID;
    private String branchName;
    private String email;
    private String cellphone;
    private int companyID;
    private List<BeaconDTO> beaconList; 
    
    public BranchDTO(Branch a) {
        branchID = a.getBranchID();
        companyID = a.getCompany().getCompanyID();
        branchName = a.getBranchName();
        email = a.getEmail();
        cellphone = a.getCellphone();
        
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public List<BeaconDTO> getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(List<BeaconDTO> beaconList) {
        this.beaconList = beaconList;
    }
    
}
