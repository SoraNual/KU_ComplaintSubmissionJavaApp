package ku.cs.form.models;

public class Report {
    private String topic;
    private String category;
    private String detail;
    private String status; // 3 status : pending, in progress, finish
    private int votePoint;

    public Report(String topic, String category, String detail, String status, int votePoint) {
        this.topic = topic;
        this.category = category;
        this.detail = detail;
        this.status = status;
        this.votePoint = votePoint;
    }

    public Report(String topic, String category, String detail) {
        this(topic, category, detail, "pending", 0);
    }

    public String getTopic() { return topic; }
    public String getCategory() { return category; }
    public String getDetail() { return detail; }
    public String getStatus() { return status; }
    public int getVotePoint() { return votePoint; }
    public void addVotePoint() { votePoint++; }

    @Override
    public String toString() {
        return "[" + getTopic() + "]" + " " + getStatus() + " " + getVotePoint();
    }
}
