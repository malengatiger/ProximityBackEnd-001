/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.dto;

import com.boha.proximity.data.Company;
import java.util.List;

/**
 *
 * @author aubreyM
 */
public class CompanyDTO {
    private int companyID;
    private String companyName;
    private String email;
    private String cellphone;
    private List<BranchDTO> branchList;
    
    public CompanyDTO(Company a) {
        companyID = a.getCompanyID();
        companyName = a.getCompanyName();
        email = a.getEmail();
        cellphone = a.getCellphone();
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public List<BranchDTO> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<BranchDTO> branchList) {
        this.branchList = branchList;
    }
    
}
