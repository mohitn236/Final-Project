package ca.ucalgary.edu.ensf380;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdvertisementTest {

    private Advertisement ad;
    private int testId = 1;
    private String testTitle = "Test Title";
    private String testDescription = "Test Description";
    private byte[] testMediaFile = {1, 2, 3, 4, 5};

    @BeforeEach
    public void setUp() {
        ad = new Advertisement(testId, testTitle, testDescription, testMediaFile);
    }

    @Test
    public void testGetId() {
        assertEquals(testId, ad.getId(), "The ID should be the same as the one provided in the constructor.");
    }

    @Test
    public void testGetTitle() {
        assertEquals(testTitle, ad.getTitle(), "The title should be the same as the one provided in the constructor.");
    }

    @Test
    public void testGetDescription() {
        assertEquals(testDescription, ad.getDescription(), "The description should be the same as the one provided in the constructor.");
    }

    @Test
    public void testGetMediaFile() {
        assertArrayEquals(testMediaFile, ad.getMediaFile(), "The media file array should be the same as the one provided in the constructor.");
    }
}
