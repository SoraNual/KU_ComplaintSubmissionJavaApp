package ku.cs.form.models;

public class Staff extends User {

    private String agency;
    public Staff(String name, String username, String password, String agency) {
        super(name, username, password);
        this.agency = agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAgency() {
        return agency;
    }

    @Override
    public String toString() {
        //loginTime.format(format) => change LocalDateTime to string
        return getLoginTime()+ "," + getUsername() + ","+ getPassword() +","+ this.getClass().getSimpleName().toLowerCase() +","+getName()+","+ "active,0," + getAgency();
    }
}
