package com.Petching.petching.global.api.s3.controller;

import com.Petching.petching.global.aws.s3.service.S3Service;
import com.Petching.petching.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;

    private final UserService userService;

    public S3Controller(S3Service s3Service, UserService userService) {
        this.s3Service = s3Service;
        this.userService = userService;
    }

    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postToS3(@Valid @RequestParam("image") MultipartFile image){

        String imageUrl = s3Service.uploadImageToS3(image);

        return new ResponseEntity(imageUrl, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFile(
            @Valid @RequestParam(value = "imgUrl") String imgUrl ){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(s3Service.deleteImage(imgUrl));
    }

}
