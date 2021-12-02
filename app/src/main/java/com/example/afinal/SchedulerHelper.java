package com.example.afinal;

public class SchedulerHelper {
    String date, reason, username, provider, identify;

    public SchedulerHelper(){

    }

    public SchedulerHelper(String date, String reason, String username, String provider, String identify) {
        this.date = date;
        this.reason = reason;
        this.username = username;
        this.provider = provider;
        this.identify = identify;
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

    public String getProvider() { return provider; }

    public void setProvider(String provider) { this.provider = provider; }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }
}
