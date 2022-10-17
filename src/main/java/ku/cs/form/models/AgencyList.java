package ku.cs.form.models;

import java.util.ArrayList;

public class AgencyList {

    private ArrayList<Agency> agencies;

    public AgencyList() {
        this.agencies = new ArrayList<>();
    }
    public void addAgency(Agency agency){
        agencies.add(agency);
    }

    public ArrayList<Agency> getAgencies() {
        return agencies;
    }

}
