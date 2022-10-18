package ku.cs.form.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportHashMap {
    private HashMap<String, UserReport> reports;

    public ReportHashMap() {
        this.reports = new HashMap<>();
    }
    public void addReport(UserReport report) {
        reports.put(report.getUsername(), report);
    }
    public void setRequest(String username, String requestDetail){
        reports.get(username).setRequest_unbanned_detail(requestDetail);
    }

    public void setReports(HashMap<String, UserReport> reports) {
        this.reports = reports;
    }

    public void deleteReport(String username){
        reports.remove(username);
    }
    public HashMap<String, UserReport> getAllReports() {
        return reports;
    }
}
