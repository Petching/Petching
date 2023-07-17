package com.Petching.petching.global.aws.s3.service;

import com.Petching.petching.global.aws.s3.dto.S3FileDto;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.utils.GenerateName;
import com.amazonaws.Response;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public List<S3FileDto> uploadFiles(String uploadTo, List<MultipartFile> multipartFiles) {

        List<S3FileDto> s3files = new ArrayList<>();

        String uploadFilePath = uploadTo;

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileName = multipartFile.getOriginalFilename();
            if(originalFileName==null)
                throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);

            String uploadFileName = getFolderName() +"-"+ generateName.generateFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {

                // ex) 구분(boards||profiles||cares)/년/월/일/파일.확장자
                String keyName = uploadFilePath + "/" + uploadFileName;

                // S3에 폴더 및 파일 업로드
                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata));


                // S3에 업로드한 폴더 및 파일 URL
                uploadFileUrl = createFileUrl(uploadFilePath, uploadFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }

            s3files.add(
                    S3FileDto.builder()
                            .originalFileName(originalFileName)
                            .uploadFileName(uploadFileName)
                            .uploadFilePath(uploadFilePath)
                            .uploadFileUrl(uploadFileUrl)
                            .build());

            amazonS3Client.shutdown();
        }


        return s3files;
    }


    /**
     * S3에 이미지 파일 업로드
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
            String imageUrl = createFileUrl(fileName);

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

    public String deleteFile(String from, String url) {

        String result = "Success";
        boolean isObjectExist = false;

        // 이미지 url 에서 file name 추출
        String objectName = getFileName(url, from);

        String keyName = from + "/" + objectName;

        // 버킷에 이미지 있는 지 확인
        if(!StringUtils.isEmpty(objectName)){
            isObjectExist = amazonS3Client.doesObjectExist(bucketName, keyName);
        }


        // 있으면 지우고 아니면 result 수정
        if (isObjectExist) {
            amazonS3Client.deleteObject(bucketName, keyName);
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
    public String getFileName(String url, String from){

        return url.replace("https://s3." + region + ".amazonaws.com/" + bucketName + "/" + from + "/", "");

    }

    private String createFileUrl(String fileName) {
        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + fileName;
    }

    private String createFileUrl(String filePath, String fileName) {
        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + filePath + "/" + fileName;
    }

    /**
     * 년/월/일 폴더명 반환
     */
    private String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }
}
