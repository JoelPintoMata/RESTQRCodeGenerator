package qrCodeGenerator.controller;

import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qrCodeGenerator.component.QRCodeGenerator;

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
    public String generate(@RequestBody Map<String, Object> payload) {
        Boolean result;
        try {
            result = qrCodeGenerator.qrCodeGenerator(payload.get("name").toString(), payload.get("url").toString());
        } catch (WriterException | IOException e) {
            LOGGER.error("Error {} with cause {}", e.getMessage(), e.getCause());
            result = false;
        }
        LOGGER.info("Result: {}", result);
        return result.toString();
    }
}