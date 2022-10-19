package ku.cs.form.models;

import java.util.HashMap;

public class ComplaintReportHashMap {

    private HashMap<String, ComplaintReport> reports;

    public ComplaintReportHashMap() {
        this.reports = new HashMap<>();
    }

    public void addReport(ComplaintReport report) {
        reports.put(report.getSubmitTime(),report);
    }

    public void deleteReport(String submitTime){
        reports.remove(submitTime);
    }

    public HashMap<String, ComplaintReport> getAllReports() {
        return reports;
    }

    @Override
    public String toString() {
        return "ComplaintReportHashMap{" +
                "reports=" + reports +
                '}';
    }
}
