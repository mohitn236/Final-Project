package ca.ucalgary.edu.ensf380;

public class Advertisement {
    private int id;
    private String title;
    private String description;
    private String mediaFile;
    private String mediaType;

    public Advertisement(int id, String title, String description, String mediaFile, String mediaType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mediaFile = mediaFile;
        this.mediaType = mediaType;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaFile() {
        return mediaFile;
    }

    public String getMediaType() {
        return mediaType;
    }
}
