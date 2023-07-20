package com.example.umm.common.config;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class AwsS3UpLoader {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String name) throws IOException {  // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        String fileName = name + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        uploadFile.delete();    // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
    public void deleteS3(String filePath) throws Exception {
        try{
            String key = filePath.substring(56); // 폴더/파일.확장자

            try {
                amazonS3Client.deleteObject(bucket, key);
            } catch (AmazonServiceException e) {
                log.info(e.getErrorMessage());
            }

        } catch (Exception exception) {
            log.info(exception.getMessage());
        }
        log.info("[S3Uploader] : S3에 있는 파일 삭제");
    }

    /**
     * 로컬에 저장된 파일 지우기
     * @param targetFile : 저장된 파일
     */
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("[파일 업로드] : 파일 삭제 성공");
            return;
        }
        log.info("[파일 업로드] : 파일 삭제 실패");
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(UUID.randomUUID().toString());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
                return Optional.of(convertFile);
            }
        }
        return Optional.empty();
    }
}
