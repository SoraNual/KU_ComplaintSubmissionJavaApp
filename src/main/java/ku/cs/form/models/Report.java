package ku.cs.form.models;

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
    public void addVotePoint(int point) { votePoint+=point; }

    public void setVotePoint(int votePoint) {
        this.votePoint = votePoint;
    }

    @Override
    public String toString() {
        return "[" + getTopic() + "]" + " " + getStatus() + " " + getVotePoint();
    }



    public void changeData() {
        String filePath = "data" + File.separator + "reports.csv";
        File file = new File(filePath);
        checkFileIsExisted();

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file,true);
            buffer = new BufferedWriter(writer);

            String line = topic+","+category+","+detail+","+status+","+votePoint;
            buffer.append(line);
            buffer.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void checkFileIsExisted() {
        File file = new File("data");
        if(!file.exists()) {
            file.mkdirs();
        }

        String filePath = "data" + File.separator + "reports.csv";
        file = new File(filePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
