package ir.shariaty.notes.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String title;
    private String description;
    private String date;
    private String noteID;
    private String publisher;


    public Note() {
    }

    public Note(String title, String description, String date, String noteID, String publisher) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.noteID = noteID;
        this.publisher = publisher;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
        noteID = in.readString();
        publisher = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    public void setNoteID(String noteid) {
        this.noteID = noteid;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getNoteID() {
        return noteID;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(noteID);
        dest.writeString(publisher);
    }
}
