package ca.ucalgary.edu.ensf380;

class Advertisement {
    private int id;
    private String title;
    private String description;
    private byte[] mediaFile;

    public Advertisement(int id, String title, String description, byte[] mediaFile) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mediaFile = mediaFile;
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

    public byte[] getMediaFile() {
        return mediaFile;
    }
}
