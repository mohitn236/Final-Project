package ca.ucalgary.edu.ensf380;

import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * The SVGHelper class provides utility methods for loading and converting SVG files to BufferedImage objects.
 */
public class SVGHelper {

    /**
     * Loads an SVG image from the specified file path and converts it to a BufferedImage.
     *
     * @param svgFilePath the path to the SVG file
     * @return a BufferedImage representation of the SVG image, or null if an error occurs
     */
    public static BufferedImage loadSVGImage(String svgFilePath) {
        try {
            // Create a PNG transcoder
            PNGTranscoder transcoder = new PNGTranscoder();

            // Set the transcoder input
            InputStream inputStream = new FileInputStream(svgFilePath);
            TranscoderInput input = new TranscoderInput(inputStream);

            // Set the transcoder output
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            // Perform the transcoding
            transcoder.transcode(input, output);

            // Create a BufferedImage from the output
            byte[] imageData = outputStream.toByteArray();
            return ImageIO.read(new ByteArrayInputStream(imageData));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
