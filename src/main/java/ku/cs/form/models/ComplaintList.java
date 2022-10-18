package ku.cs.form.models;

import ku.cs.form.services.Filterer;

import java.util.ArrayList;

public class ComplaintList {
    private ArrayList<Complaint> complaints;

    public ComplaintList() { this.complaints = new ArrayList<>(); }
    public void addComplaint(Complaint complaint) { complaints.add(complaint); }
    public ArrayList<Complaint> getAllComplaints() { return complaints; }
    public Complaint find(Complaint complaint) {
        Complaint found = null;
        for (Complaint data: complaints){
            if(data.getTopic().equals(complaint.getTopic()) && data.getSubmitTime().equals(complaint.getSubmitTime()))
                found = data;
        }
        if (found == null) {
            throw new RuntimeException(found.getTopic() + " not found");
        }
        return found;
    }
    public ComplaintList filterBy(Filterer<Complaint> filterer){
        ComplaintList filtered = new ComplaintList();
        for(Complaint complaint: complaints){
            Complaint found = find(complaint);
            if (filterer.filter(found)){
                filtered.addComplaint(found);
            }
        }
        return filtered;
    }
}
