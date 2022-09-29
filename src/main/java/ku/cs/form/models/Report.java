package ku.cs.form.models;

public class Report {
    private String topic;
    private String complainantUsername; //ผู้ร้องเรียน
    private String category;
    private String detail;
    private String status; // 3 status : pending, in progress, finish
    private int votePoint;

    public Report(String topic, String complainantUsername, String category, String detail, String status, int votePoint) {
        this.topic = topic;
        this.complainantUsername = complainantUsername;
        this.category = category;
        this.detail = detail;
        this.status = status;
        this.votePoint = votePoint;
    }

    public Report(String topic, String complainantUsername, String category, String detail) {
        this(topic, complainantUsername, category, detail, "pending", 0);
    }

    public String getTopic() { return topic; }
    public String getCategory() { return category; }
    public String getDetail() { return detail; }
    public String getStatus() { return status; }
    public int getVotePoint() { return votePoint; }

    public String getComplainantUsername() {
        return complainantUsername;
    }

    public void addVotePoint() { votePoint++; }

    @Override
    public String toString() {
        return "หัวข้อ: " + getTopic() + "\n" +
                "รายละเอียด: " + getDetail() + "\n" +
                "สถานะ: " + getStatus() + "\n" +
                "คะแนนโหวต: " + getVotePoint();
    }
}
