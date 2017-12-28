package qrCodeGenerator.controller;

import com.google.zxing.WriterException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qrCodeGenerator.component.QRCodeGenerator;

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

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/health")
    @ResponseBody
    String health() {
        return "green";
    }

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

    @RequestMapping(value = "/generateAndGet", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    byte[] generateAndGet(@RequestBody Map<String, Object> payload) {
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
}