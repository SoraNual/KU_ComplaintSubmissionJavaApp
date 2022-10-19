package ku.cs.form.models;

import java.util.HashMap;

public class UserReportHashMap {
    private HashMap<String, UserReport> reports;

    public UserReportHashMap() {
        this.reports = new HashMap<>();
    }
    public void addReport(UserReport report) {
        reports.put(report.getUsername(), report);
    }
    public void setRequest(String username, String requestDetail){
        reports.get(username).setRequestUnbannedDetail(requestDetail);
    }

    public void deleteReport(String username){
        reports.remove(username);
    }
    public HashMap<String, UserReport> getAllReports() {
        return reports;
    }
}
