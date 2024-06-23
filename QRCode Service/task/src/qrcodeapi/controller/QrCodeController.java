package qrcodeapi.controller;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qrcodeapi.exception.ValidationException;
import qrcodeapi.model.ImageType;
import qrcodeapi.service.QrCodeGeneratorService;

import java.awt.image.BufferedImage;
//import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
public class QrCodeController {
    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QrCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }

    @GetMapping("/health")
    public ResponseEntity<HttpStatus> getHealthStatus() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/qrcode")
    public ResponseEntity<BufferedImage> getQrCodeImage(@RequestParam(required = false, defaultValue = "250") int size,
                                                        @RequestParam(required = false, defaultValue = "png") String type,
                                                        @RequestParam(required = false, defaultValue = "L") String correction,
                                                        @RequestParam String contents) {


        BufferedImage bufferedImage = qrCodeGeneratorService.generateQrCodeImage(size, type, correction, contents);
        ImageType imageType = ImageType.fromString(type);

        return ResponseEntity
                .ok()
                .contentType(imageType.getMediaType())
                .body(bufferedImage);
    }
}