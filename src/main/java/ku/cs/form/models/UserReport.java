package ku.cs.form.models;

public class UserReport {
    private String username;
    private String complaint_category;
    private String complaint_detail;
    private String request_unbanned_detail;

    public UserReport(String username, String complaint_category, String complaint_detail, String request_permission_detail) {
        this.username = username;
        this.complaint_category = complaint_category;
        this.complaint_detail = complaint_detail;
        this. request_unbanned_detail = request_permission_detail;
    }

    public String getComplaint_category() {
        return complaint_category;
    }

    public String getComplaint_detail() {
        return complaint_detail;
    }

    public String getRequest_permission_detail() {
        return  request_unbanned_detail;
    }

    @Override
    public String toString() {
        return "UserComplaint{" +
                "username='" + username + '\'' +
                ", complaint_category='" + complaint_category + '\'' +
                ", complaint_detail='" + complaint_detail + '\'' +
                ", request_permission_detail='" +  request_unbanned_detail + '\'' +
                '}';
    }
}
