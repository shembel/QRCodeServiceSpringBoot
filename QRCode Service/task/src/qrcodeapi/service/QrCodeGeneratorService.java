package qrcodeapi.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import qrcodeapi.exception.ValidationException;
import qrcodeapi.model.ImageType;

import java.awt.image.BufferedImage;
import java.util.Map;

@Service
public class QrCodeGeneratorService {
    private static final int MIN_SIZE = 150;
    private static final int MAX_SIZE = 350;
    private static final String SIZE_ERROR_MESSAGE = "Image size must be between 150 and 350 pixels";
    private static final String TYPE_ERROR_MESSAGE = "Only png, jpeg and gif image types are supported";
    private static final String CORRECTION_ERROR_MESSAGE = "Permitted error correction levels are L, M, Q, H";

    private final QRCodeWriter writer = new QRCodeWriter();

    public BufferedImage generateQrCodeImage(int size, String type, String correctionLvl, String contents) {
        BufferedImage bufferedImage = null;
        String normalizedCorrectionLvl = correctionLvl.toUpperCase();

        validateContents(contents);
        validateSize(size);
        validateErrorCorrectionLevel(normalizedCorrectionLvl);
        validateType(type);

        Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, normalizedCorrectionLvl);

        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            System.out.println("Error while generating QR code from provided data.");
            e.printStackTrace();
        }

        return bufferedImage;
    }

    private void validateContents(String contents) {
        if (contents == null || contents.isEmpty() || contents.isBlank()) {
            throw new ValidationException("Contents cannot be null or blank", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new ValidationException(SIZE_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateErrorCorrectionLevel(String correctionLvl) {
        try {
            ErrorCorrectionLevel.valueOf(correctionLvl);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(CORRECTION_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateType(String type) {
        try {
            ImageType.fromString(type);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(TYPE_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }
}
