package edu.utep.cs.cs4330.ires10;

public class Report {

    private long date;
    private String incident;
    private double latitude;
    private double longitude;
    private String description;

    //Alert constructor
    public Report(long date, double latitude, double longitude){
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //All type of Reports constructor
    public Report(long date, String incident, double latitude, double longitude, String description){
        this.date = date;
        this.incident = incident;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
