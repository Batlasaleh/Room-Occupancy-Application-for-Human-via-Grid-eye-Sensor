package com.example.room_occupancy;

public class sendproblemDB {
    private String type;
    private String roomnum;
    private String problem;
    private String id;

    public sendproblemDB() {
    }

    public sendproblemDB(String roomnum, String problem, String type, String id) {
        this.roomnum = roomnum;
        this.problem = problem;
        this.type=type;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getRoomnum() {
        return roomnum;
    }

    public String getProblem() {
        return problem;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
