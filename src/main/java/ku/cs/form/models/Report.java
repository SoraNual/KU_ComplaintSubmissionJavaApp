package ku.cs.form.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private String topic;
    private String complainantUsername; //ผู้ร้องเรียน
    private LocalDateTime submitTime;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private String category;
    private String detail;
    private String status; // 3 status : pending, in progress, finish
    private int votePoint;

    public Report(String topic, String complainantUsername, String category, String detail, String status, int votePoint) {
        this.topic = topic;
        this.complainantUsername = complainantUsername;
        this.submitTime = LocalDateTime.now();
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

    public String getSubmitTime() {
        return submitTime.format(format);
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public void setSubmitTime(String s) {
        //change string to LocalDateTime
        this.submitTime = LocalDateTime.parse(s,format);
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
