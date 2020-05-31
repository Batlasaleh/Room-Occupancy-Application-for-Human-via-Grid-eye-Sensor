package com.example.room_occupancy;

public class AddAnnouncementsDB {
    private String announcementType;
    private String title;
    private String message;
    private String id;


    public AddAnnouncementsDB(String announcementType, String title, String message, String id) {
        this.announcementType = announcementType;
        this.title = title;
        this.message = message;
        this.id=id;
    }

    public AddAnnouncementsDB() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
