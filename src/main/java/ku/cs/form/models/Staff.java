package ku.cs.form.models;

public class Staff extends User {

    private String agency;
    public Staff(String name, String username, String password,String agency) {
        super(name, username, password);
        this.agency = agency;
    }

    public String getAgency() {
        return agency;
    }
}
