/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.dto;

import java.util.List;

/**
 *
 * @author aubreyM
 */
public class ResponseDTO {
    private int statusCode;
    private String message;
    
    private List<BeaconDTO> beaconList;
    private List<BranchDTO> branchList;
    private CompanyDTO company;
    private BranchDTO branch;
    private BeaconDTO beacon;
    private BeaconDataItemDTO beaconDataItem;
    private List<String> imageFileNames;
    private List<CompanyDTO> companyList;
    private List<VisitorDTO> visitorList;
    private List<VisitorTrackDTO> visitorTrackList;
    private List<VisitorTrackDTO> visitorTrackListSortedByBeacon;
    private VisitorDTO visitor;
    
    private List<ErrorStoreAndroidDTO> errorStoreAndroidList;
    private List<ErrorStoreDTO> errorStoreList;
    private String log;

    public List<VisitorTrackDTO> getVisitorTrackListSortedByBeacon() {
        return visitorTrackListSortedByBeacon;
    }

    public void setVisitorTrackListSortedByBeacon(List<VisitorTrackDTO> visitorTrackListSortedByBeacon) {
        this.visitorTrackListSortedByBeacon = visitorTrackListSortedByBeacon;
    }

    public List<VisitorTrackDTO> getVisitorTrackList() {
        return visitorTrackList;
    }

    public void setVisitorTrackList(List<VisitorTrackDTO> visitorTrackList) {
        this.visitorTrackList = visitorTrackList;
    }

    public VisitorDTO getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorDTO visitor) {
        this.visitor = visitor;
    }

    
    
    public List<VisitorDTO> getVisitorList() {
        return visitorList;
    }
    

    public void setVisitorList(List<VisitorDTO> visitorList) {
        this.visitorList = visitorList;
    }

    
    public List<ErrorStoreAndroidDTO> getErrorStoreAndroidList() {
        return errorStoreAndroidList;
    }

    public void setErrorStoreAndroidList(List<ErrorStoreAndroidDTO> errorStoreAndroidList) {
        this.errorStoreAndroidList = errorStoreAndroidList;
    }

    public List<ErrorStoreDTO> getErrorStoreList() {
        return errorStoreList;
    }

    public void setErrorStoreList(List<ErrorStoreDTO> errorStoreList) {
        this.errorStoreList = errorStoreList;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
    
    
    
    public static final int DATABASE_ERROR = 100, SERVER_ERROR = 101;

    public List<CompanyDTO> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<CompanyDTO> companyList) {
        this.companyList = companyList;
    }

    public List<BranchDTO> getBranchList() {
        return branchList;
    }

    public List<String> getImageFileNames() {
        return imageFileNames;
    }

    public void setImageFileNames(List<String> imageFileNames) {
        this.imageFileNames = imageFileNames;
    }

    public void setBranchList(List<BranchDTO> branchList) {
        this.branchList = branchList;
    }

    
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public BeaconDataItemDTO getBeaconDataItem() {
        return beaconDataItem;
    }

    public void setBeaconDataItem(BeaconDataItemDTO beaconDataItem) {
        this.beaconDataItem = beaconDataItem;
    }
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BeaconDTO> getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(List<BeaconDTO> beaconList) {
        this.beaconList = beaconList;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public BeaconDTO getBeacon() {
        return beacon;
    }

    public void setBeacon(BeaconDTO beacon) {
        this.beacon = beacon;
    }
    
    
}
