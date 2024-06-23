package qrcodeapi.model;

import org.springframework.http.MediaType;

public enum ImageType {
    PNG("PNG", MediaType.IMAGE_PNG),
    JPEG("JPEG", MediaType.IMAGE_JPEG),
    GIF("GIF", MediaType.IMAGE_GIF);

    private final String type;
    private final MediaType mediaType;

    ImageType(String type, MediaType mediaType) {
        this.type = type;
        this.mediaType = mediaType;
    }

    public String getType() {
        return type;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public static ImageType fromString(String type) {
        for (ImageType imageType : ImageType.values()) {
            if (imageType.getType().equalsIgnoreCase(type)) {
                return imageType;
            }
        }
        throw new IllegalArgumentException("Unknown image type: " + type);
    }
}