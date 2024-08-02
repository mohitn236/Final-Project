package ca.ucalgary.edu.ensf380;

/**
 * The Advertisement class represents an advertisement with an ID, title, description, and media file.
 * It provides methods to access these properties.
 */
class Advertisement {
    private int id;
    private String title;
    private String description;
    private byte[] mediaFile;

    /**
     * Constructs an Advertisement object with the specified ID, title, description, and media file.
     *
     * @param id          the unique identifier of the advertisement
     * @param title       the title of the advertisement
     * @param description the description of the advertisement
     * @param mediaFile   the media file of the advertisement, represented as a byte array
     */
    public Advertisement(int id, String title, String description, byte[] mediaFile) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mediaFile = mediaFile;
    }

    /**
     * Returns the ID of the advertisement.
     *
     * @return the ID of the advertisement
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title of the advertisement.
     *
     * @return the title of the advertisement
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the advertisement.
     *
     * @return the description of the advertisement
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the media file of the advertisement.
     *
     * @return the media file of the advertisement as a byte array
     */
    public byte[] getMediaFile() {
        return mediaFile;
    }
}
