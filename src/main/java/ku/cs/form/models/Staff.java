package ku.cs.form.models;

public class Staff extends User {

    private String agency;
    public Staff(String name, String username, String password,String agency,String rectangleColorWeb,String textColorWeb,String backgroundColorWeb,String buttonColorWeb) {
        super(name, username, password,rectangleColorWeb,textColorWeb,backgroundColorWeb,buttonColorWeb);
        this.agency = agency;
    }

    public Staff(String name, String username, String password, String agency) {
        super(name, username, password);
        this.agency = agency;
    }

    public String getAgency() {
        return agency;
    }

    @Override
    public String toString() {
        //loginTime.format(format) => change LocalDateTime to string
        return getUsername()+","+getPassword()+","+this.getClass().getSimpleName()+","+getName()+","+getUserStatus()+","+getLoginAttempt()+","+getAgency()+","+ rectangleColor +","+textColor+","+backgroundColor+","+buttonColor;
    }
}
