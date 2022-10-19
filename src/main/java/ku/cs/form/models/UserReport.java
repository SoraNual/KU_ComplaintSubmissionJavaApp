package ku.cs.form.models;

public class UserReport {
    private String username;
    private String complaintCategory;
    private String complaintDetail;
    private String requestUnbannedDetail;

    public UserReport(String username, String complaintCategory, String complaintDetail, String request_permission_detail) {
        this.username = username;
        this.complaintCategory = complaintCategory;
        this.complaintDetail = complaintDetail;
        this.requestUnbannedDetail = request_permission_detail;
    }

    public String getUsername() {
        return username;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public String getComplaintDetail() {
        return complaintDetail;
    }

    public String getRequest_permission_detail() {
        return requestUnbannedDetail;
    }

    public void setRequestUnbannedDetail(String requestUnbannedDetail) {
        this.requestUnbannedDetail = requestUnbannedDetail;
    }

    public String writeAble(){
        return username+","+ complaintCategory +","+ complaintDetail;
    }
}
