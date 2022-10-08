package ku.cs.form.models;

public class ReportCategory {
    String name;
    String additionalDetailTitle;
    String additionalImageTitle;
    Boolean imageNeeded;

    public ReportCategory(String name, String additionalDetailTitle, String additionalImageTitle, Boolean imageNeeded) {
        this.name = name;
        this.additionalDetailTitle = additionalDetailTitle;
        this.additionalImageTitle = additionalImageTitle;
        this.imageNeeded = imageNeeded;
    }

    public String getName() {
        return name;
    }

    public String getAdditionalDetailTitle() {
        return additionalDetailTitle;
    }

    public String getAdditionalImageTitle() {
        return additionalImageTitle;
    }

    public Boolean getImageNeeded() {
        return imageNeeded;
    }

    @Override
    public String toString() {
        return name;
    }
}
