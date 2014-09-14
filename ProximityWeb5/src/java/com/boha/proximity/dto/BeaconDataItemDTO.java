/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.boha.proximity.dto;

import com.boha.proximity.data.BeaconDataItem;

/**
 *
 * @author aubreyM
 */
public class BeaconDataItemDTO {
  private int beaconDataItemID;
    private String imageUrl;
    private String html;
    private String text;
    private int beaconID;  
    public BeaconDataItemDTO(BeaconDataItem a) {
        beaconDataItemID = a.getBeaconDataItemID();
        imageUrl = a.getImageUrl();
        html = a.getHtml();
        text = a.getText();
        beaconID = a.getBeacon().getBeaconID();
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

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }
    
}
