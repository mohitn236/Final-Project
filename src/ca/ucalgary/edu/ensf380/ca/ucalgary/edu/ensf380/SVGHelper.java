package ca.ucalgary.edu.ensf380;
// Necessary batik imports needed 
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class SVGHelper {
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


