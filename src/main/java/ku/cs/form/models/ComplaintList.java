package ku.cs.form.models;

import ku.cs.form.services.Filterer;

import java.util.ArrayList;

public class ComplaintList {
    private ArrayList<Complaint> complaints;

    public ComplaintList() { this.complaints = new ArrayList<>(); }
    public void addReport(Complaint complaint) { complaints.add(complaint); }
    public ArrayList<Complaint> getAllReports() { return complaints; }

    public ComplaintList filterBy(Filterer<Complaint> filterer) {
        ComplaintList filtered = new ComplaintList();


        return filtered;
    }

//    public Dictionary filterBy(Filterer<Word> filterer) {
//        Dictionary filtered = new Dictionary();
//        for (String key: words.keySet()) {
//            Word found = find(key);
//            if (filterer.filter(found)) {
//                filtered.addWord(key, found);
//            }
//        }
//
//        return filtered;
//    }
}
