package ku.cs.form.models;

import java.util.ArrayList;

public class ReportList {
    private ArrayList<Report> reports;

    public ReportList() { this.reports = new ArrayList<>(); }
    public void addReport(Report report) { reports.add(report); }
    public ArrayList<Report> getAllReports() { return reports; }
}
