/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.data;

import com.boha.proximity.data.Company;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author aubreyM
 */
@Entity
@Table(name = "errorStoreAndroid")
@NamedQueries({
    @NamedQuery(name = "ErrorStoreAndroid.findByCompany", 
            query = "SELECT e FROM ErrorStoreAndroid e where e.company.companyID = :id order by e.errorDate desc"),
    @NamedQuery(name = "ErrorStoreAndroid.findByPeriod", 
            query = "SELECT e FROM ErrorStoreAndroid e WHERE e.errorDate BETWEEN :from AND :to order by e.errorDate desc")
})
public class ErrorStoreAndroid implements Serializable {
    @JoinColumn(name = "companyID", referencedColumnName = "companyID")
    @ManyToOne(optional = false)
    private Company company;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "errorStoreAndroidID")
    private int errorStoreAndroidID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "errorDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date errorDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "packageName")
    private String packageName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "appVersionName")
    private String appVersionName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "appVersionCode")
    private String appVersionCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "brand")
    private String brand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "phoneModel")
    private String phoneModel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "androidVersion")
    private String androidVersion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "stackTrace")
    private String stackTrace;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "logCat")
    private String logCat;

    public ErrorStoreAndroid() {
    }

    public ErrorStoreAndroid(int errorStoreAndroidID) {
        this.errorStoreAndroidID = errorStoreAndroidID;
    }

    public ErrorStoreAndroid(int errorStoreAndroidID, Date errorDate, String packageName, String appVersionName, String appVersionCode, String brand, String phoneModel, String androidVersion, String stackTrace, String logCat) {
        this.errorStoreAndroidID = errorStoreAndroidID;
        this.errorDate = errorDate;
        this.packageName = packageName;
        this.appVersionName = appVersionName;
        this.appVersionCode = appVersionCode;
        this.brand = brand;
        this.phoneModel = phoneModel;
        this.androidVersion = androidVersion;
        this.stackTrace = stackTrace;
        this.logCat = logCat;
    }

    public int getErrorStoreAndroidID() {
        return errorStoreAndroidID;
    }

    public void setErrorStoreAndroidID(int errorStoreAndroidID) {
        this.errorStoreAndroidID = errorStoreAndroidID;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getLogCat() {
        return logCat;
    }

    public void setLogCat(String logCat) {
        this.logCat = logCat;
    }

    

    @Override
    public String toString() {
        return "com.boha.golfkids.data.ErrorStoreAndroid[ errorStoreAndroidID=" + errorStoreAndroidID + " ]";
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    
}
