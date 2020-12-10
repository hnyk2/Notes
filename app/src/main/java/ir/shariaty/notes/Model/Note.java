package ir.shariaty.notes.Model;
public class Note {

    private String title;
    private String description;
    private String date;
    private String noteid;
    private String publisher;

    public Note(String title, String description, String date, String noteid, String publisher) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.noteid = noteid;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getNoteid() {
        return noteid;
    }

    public String getPublisher() {
        return publisher;
    }

}
