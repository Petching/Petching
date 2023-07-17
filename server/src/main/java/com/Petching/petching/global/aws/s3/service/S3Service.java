package com.Petching.petching.global.aws.s3.service;

import com.Petching.petching.utils.GenerateName;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3Service{


    String region = "ap-northeast-2";
    String bucketName = "petching.image";

    private final GenerateName generateName;

    private final AmazonS3Client amazonS3Client;

    public S3Service(GenerateName generateName, AmazonS3Client amazonS3Client) {
        this.generateName = generateName;
        this.amazonS3Client = amazonS3Client;
    }


    /**
     * S3에 파일 업로드
     */
    public String uploadImageToS3(MultipartFile image) {

        try {
            // 업로드할 이미지 파일의 이름 생성
            String fileName = null;
            if (image.getOriginalFilename() != null) {
                fileName = generateName.generateFileName(image.getOriginalFilename());
            } else return null;

            // 이미지 파일을 로컬에 저장
            Path tempFile = Files.createTempFile(fileName, "");
            image.transferTo(tempFile.toFile());

            // S3에 이미지 업로드
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, tempFile.toFile());
            amazonS3Client.putObject(putObjectRequest);


            // 업로드된 이미지의 URL 생성
            String imageUrl = createFileName(fileName);

            return imageUrl;
        } catch (Exception e) {

            // 업로드 실패 처리
            e.printStackTrace();
            return null;
        } finally {
            // S3 클라이언트 종료
            amazonS3Client.shutdown();
        }

    }


    /**
     * S3에 업로드된 파일 삭제
     */
    public String deleteImage(String imgUrl) {

        String result = "Success";
        boolean isObjectExist = false;

        // 이미지 url 에서 file name 추출
        String objectName = getFileName(imgUrl);

        // 버킷에 이미지 있는 지 확인
        if(!StringUtils.isEmpty(objectName)){
            isObjectExist = amazonS3Client.doesObjectExist(bucketName, objectName);
        }


        // 있으면 지우고 아니면 result 수정
        if (isObjectExist) {
            amazonS3Client.deleteObject(bucketName, objectName);
        } else {
            result = "File not found";
        }

        // S3 클라이언트 종료
        amazonS3Client.shutdown();

        return result;
    }

    public String getFileName(String url){

        return url.replace("https://s3." + region + ".amazonaws.com/" + bucketName + "/", "");

    }

    private String createFileName(String fileName) {
        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + fileName;
    }

}
