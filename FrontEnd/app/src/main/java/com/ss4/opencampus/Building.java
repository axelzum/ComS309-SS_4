package com.ss4.opencampus;

public class Building {

    public String buildingName;

    public String abbrev;

    public String address;

    public Double latitude;

    public Double longitude;

    public Building() {
    }

    public Building(String buildingName, String abbrev, String address, Double latitude, Double longitude) {
        this.buildingName = buildingName;
        this.abbrev = abbrev;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLatString() {
        return latitude.toString();
    }

    public String getLongString() {
        return longitude.toString();
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
