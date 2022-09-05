package ku.cs.form.models;

public class Staff extends User {

    private String agency;
    public Staff(String name, String username, String password,String agency) {
        super(name, username, password);
        this.agency = agency;
    }

    public Staff(String name, String username, String password){
        this(name,username,password,"NoAgency");
    }

    public String getAgency() {
        return agency;
    }

    @Override
    public String toString() {
        return super.toString() + " Agency : " + agency;
    }

}
