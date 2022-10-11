package ku.cs.form.models;

import java.util.ArrayList;

public class AgencyList {

    private ArrayList<String> agencies;

    public AgencyList() {
        this.agencies = new ArrayList<>();
    }
    public void addAgency(String agency){
        agencies.add(agency);
    }

    public ArrayList<String> getAgencies() {
        return agencies;
    }

    public void changeAgency(String oldAgencyName,String newAgencyName) {
        int index = agencies.indexOf(oldAgencyName);
        agencies.remove(oldAgencyName);
        agencies.add(index,newAgencyName);
    }
}
