package yazzyyas.gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String status;
    private String title;
    private String platform;
    private String notes;
    private Date date;

    public Game(Long id, String title, String platform, String notes, Date date) {
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.notes = notes;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String reminderText) {
        this.status = reminderText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
