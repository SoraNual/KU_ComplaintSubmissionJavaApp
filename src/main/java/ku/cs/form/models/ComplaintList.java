package ku.cs.form.models;

import java.util.ArrayList;

public class ComplaintList {
    private ArrayList<Complaint> complaints;

    public ComplaintList() { this.complaints = new ArrayList<>(); }
    public void addReport(Complaint complaint) { complaints.add(complaint); }
    public ArrayList<Complaint> getAllReports() { return complaints; }
}
