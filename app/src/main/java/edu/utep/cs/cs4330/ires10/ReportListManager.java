package edu.utep.cs.cs4330.ires10;

import java.util.ArrayList;
import java.util.List;

public class ReportListManager {

    private List<Report> reportsList;

    public ReportListManager(){
        reportsList = new ArrayList<>();
    }
    public List<Report> getReports(){
        return reportsList;
    }
    public void setItems(List<Report> itemList){
        this.reportsList = itemList;
    }

    public Object count(){
        return reportsList.size();
    }
    public void addReport(long date, double latitude, double longitude){
        reportsList.add(new Report(date, latitude, longitude));
    }
    public void addReport(Report report){
        reportsList.add(report);
    }
    public boolean contains(Report report){
        return reportsList.contains(report);
    }

    public Report get(int index){
        return reportsList.get(index);
    }
    public void remove(int index){
        reportsList.remove(index);
    }
    public void clear(){
        reportsList.clear();
    }
}