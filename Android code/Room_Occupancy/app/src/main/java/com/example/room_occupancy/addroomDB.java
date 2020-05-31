package com.example.room_occupancy;

public class addroomDB {
    private String sensorID;
    private String floor;
    private String  roomnum;
    private String  facilities;
    private String roomSize;
    private String  chairs;
    private String id;

    public addroomDB() {
    }

    public addroomDB(String sensorID, String floor, String roomnum, String facilities, String roomSize, String chairs, String id) {
        this.sensorID = sensorID;
        this.floor = floor;
        this.roomnum = roomnum;
        this.facilities = facilities;
        this.roomSize = roomSize;
        this.chairs = chairs;
        this.id = id;
    }
    public addroomDB(String roomnum, String facilities, String roomSize, String chairs) {
        this.roomnum=roomnum;
        this.facilities = facilities;
        this.roomSize = roomSize;
        this.chairs = chairs;

    }

    public String getId() {
        return id;
    }

    public String getSensorID() {
        return sensorID;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoomnum() {
        return roomnum;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public String getChairs() {
        return chairs;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    public void setChairs(String chairs) {
        this.chairs = chairs;
    }

    public void setId(String id) {
        this.id = id;
    }
}
