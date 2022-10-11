package ku.cs.form.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class Complaint {
    private String topic;
    private String complainantUsername; //ผู้ร้องเรียน
    private LocalDateTime submitTime;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String basicDetail;
    private String category;
    private String additionalDetail;
    private String status; // 3 status : pending, in progress, finish
    private int votePoint;
    private ArrayList<User> votedUsers;
    private String solution;

    public Complaint(String topic, String complainantUsername, String basicDetail, String category, String additionalDetail, String status, int votePoint, String solution) {
        this.topic = topic;
        this.complainantUsername = complainantUsername;
        this.submitTime = LocalDateTime.now();
        this.category = category;
        this.additionalDetail = additionalDetail;
        this.basicDetail = basicDetail;
        this.status = status;
        this.votePoint = votePoint;
        this.solution = solution;
    }

    public Complaint(String topic, String complainantUsername, String basicDetail, String category, String additionalDetail) {
        this(topic, complainantUsername, basicDetail, category, additionalDetail, "pending", 0, null);
    }

    public String getTopic() { return topic; }
    public String getCategory() { return category; }
    public String getBasicDetail() { return basicDetail; }
    public String getStatus() { return status; }
    public int getVotePoint() { return votePoint; }

    public String getComplainantUsername() {
        return complainantUsername;
    }

    public String getSubmitTime() {
        return submitTime.format(format);
    }
    public String getAdditionalDetail() {
        return additionalDetail;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public void setSubmitTime(String s) {
        //change string to LocalDateTime
        this.submitTime = LocalDateTime.parse(s,format);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addVotePoint() { votePoint++; }

    @Override
    public String toString() {
        return "<[ " + getVotePoint() + " ]> " + getTopic();
    }


}
