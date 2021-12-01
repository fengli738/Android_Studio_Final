package com.example.afinal;

public class SchedulerHelper {
    String date, reason, username;

    public SchedulerHelper(){

    }

    public SchedulerHelper(String date, String reason, String username) {
        this.date = date;
        this.reason = reason;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
