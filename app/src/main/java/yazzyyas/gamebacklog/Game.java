package yazzyyas.gamebacklog;

public class Game {

    private String title;
    private String platform;
    private String notes;

    public Game(String title, String platform, String notes) {
        this.title = title;
        this.platform = platform;
        this.notes = notes;
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
}
