package com.Petching.petching.aws.s3.client;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public interface CustomS3Client {

    String uploadImageToS3(MultipartFile image);


}
