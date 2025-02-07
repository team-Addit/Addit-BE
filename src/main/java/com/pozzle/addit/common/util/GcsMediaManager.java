package com.pozzle.addit.common.util;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class GcsMediaManager {

    @Value("${google.cloud.storage.bucketName}")
    private String bucketName;

    private final Storage storage;

    public String saveMediaFile(MultipartFile mediaFile) {
        String originalFileName = mediaFile.getOriginalFilename();

        String safeFileName = UUID.randomUUID().toString();

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileName = safeFileName + extension;

        String contentType;
        switch (extension) {
            case ".jpg":
            case ".jpeg":
                contentType = "image/jpeg";
                break;
            case ".png":
                contentType = "image/png";
                break;
            case ".gif":
                contentType = "image/gif";
                break;
            default:
                throw new IllegalArgumentException("not supported file type");
        }

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();

        try {
            storage.create(blobInfo, mediaFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }

}
