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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "branch")
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", 
            query = "SELECT b FROM Branch b order by b.company.companyID, b.branchName"),
    @NamedQuery(name = "Branch.findByCompany", 
            query = "SELECT b FROM Branch b where b.company.companyID = :id "
                    + "order by b.branchName"),
    @NamedQuery(name = "Branch.findByBranchName", 
            query = "SELECT b FROM Branch b where b.company.companyID = :id and "
                    + "b.branchName = :branchName"),
    @NamedQuery(name = "Branch.findByEmail", 
            query = "SELECT b FROM Branch b WHERE b.email = :email"),
    @NamedQuery(name = "Branch.findByCellphone", 
            query = "SELECT b FROM Branch b WHERE b.cellphone = :cellphone")})
public class Branch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "branchID")
    private Integer branchID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "branchName")
    private String branchName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 50)
    @Column(name = "cellphone")
    private String cellphone;
    
    
    @JoinColumn(name = "companyID", referencedColumnName = "companyID")
    @ManyToOne(optional = false)
    private Company company;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Beacon> beaconList;

    public Branch() {
    }

    public Branch(int branchID) {
        this.branchID = branchID;
    }

    public Branch(int branchID, String branchName, String email, String cellphone) {
        this.branchID = branchID;
        this.branchName = branchName;
        this.email = email;
        this.cellphone = cellphone;
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

  

    public List<Beacon> getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(List<Beacon> beaconList) {
        this.beaconList = beaconList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

   

    @Override
    public String toString() {
        return "com.boha.proximity.data.Branch[ branchID=" + branchID + " ]";
    }

    public Branch(int branchID, String branchName) {
        this.branchID = branchID;
        this.branchName = branchName;
    }

    public Branch(Integer branchID) {
        this.branchID = branchID;
    }

    public Branch(Integer branchID, String branchName) {
        this.branchID = branchID;
        this.branchName = branchName;
    }

   
    
}
