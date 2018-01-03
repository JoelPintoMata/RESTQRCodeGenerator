package qrCodeGenerator.controller;

import com.google.zxing.WriterException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qrCodeGenerator.component.QRCodeGenerator;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * QR code generator controller
 */
@Controller
public class QRCodeGeneratorController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @CrossOrigin(origins = "*")
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/health")
    @ResponseBody
    String health() {
        return "green";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    boolean generate(@RequestBody Map<String, Object> payload) {
        Boolean result = false;
        try {
            if(qrCodeGenerator.qrCodeGenerator(payload.get("name").toString(), payload.get("url").toString())
                    != null)
                result = true;
        } catch (WriterException | IOException e) {
            LOGGER.error("Error {} with cause {}", e.getMessage(), e.getCause());
        }
        LOGGER.info("Result: {}", result.toString());
        return result;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/generateAndGet", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    byte[] generateAndGetImage(@RequestBody Map<String, Object> payload) {
        Boolean result = false;
        byte[] bytes = new byte[0];
        try {
            File file = qrCodeGenerator.qrCodeGenerator(payload.get("name").toString(), payload.get("url").toString());
            bytes = IOUtils.toByteArray(new FileInputStream(file));
            result = true;
        } catch (WriterException | IOException e) {
            LOGGER.error("Error {} with cause {}", e.getMessage(), e.getCause());
        }
        LOGGER.info("Result: {}", result);
        return bytes;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/generateAndGetString", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    String generateAndGetString(@RequestBody Map<String, Object> payload) {
        String result = null;
        try {
            File file = qrCodeGenerator.qrCodeGenerator(payload.get("name").toString(), payload.get("url").toString());
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(bytes);

        } catch (WriterException | IOException e) {
            LOGGER.error("Error {} with cause {}", e.getMessage(), e.getCause());
        }
        LOGGER.info("Result: {}", result);

        return result;
    }
}