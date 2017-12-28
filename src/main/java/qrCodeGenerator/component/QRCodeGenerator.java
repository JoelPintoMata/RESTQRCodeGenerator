package qrCodeGenerator.component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * QRCode generator
 */
@Component
public class QRCodeGenerator {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String FILE_PATH = "images/";
    private static final String FILE_TYPE = "png";
    private static final int SIZE = 125;

    /**
     * Make sure that FILE_PATH exists
     */
    @PostConstruct
    private void postConstruct() {
        File file = new File(FILE_PATH);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
    }

    /**
     * Make sure that all images under FILE_PATH are deleted
     */
    @PreDestroy
    private void preDestroy() {
        File file = new File(FILE_PATH);
        if (file.exists() && file.isDirectory()) {
            file.delete();
        }
    }

    /**
     *
     * Generates a QRCode
     * @param name the final image file name
     * @param text the text to be included in the QRCode
     * @return a boolean with the operation result
     * @throws WriterException
     * @throws IOException
     */
    public boolean qrCodeGenerator(String name, String text) throws WriterException, IOException {
        File file = new File(FILE_PATH + name + "." + FILE_TYPE);

        // Create the ByteMatrix for the QRCode that encodes the given String
        Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(text,
                BarcodeFormat.QR_CODE, SIZE, SIZE, hintMap);

        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
                BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);

        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return ImageIO.write(image, FILE_TYPE, file);
    }
}