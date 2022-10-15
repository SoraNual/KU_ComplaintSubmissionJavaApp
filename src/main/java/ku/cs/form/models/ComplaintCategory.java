package ku.cs.form.models;

public class ComplaintCategory {
    private String name;
    private String additionalDetailTitle;
    private String additionalImageTitle;
    private Boolean imageNeeded;

    public ComplaintCategory(String name, String additionalDetailTitle, Boolean imageNeeded, String additionalImageTitle) {
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
