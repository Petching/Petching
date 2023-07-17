package com.Petching.petching.global.api.s3.controller;

import com.Petching.petching.global.aws.s3.service.S3Service;
import com.Petching.petching.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;


    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/uploads")
    public ResponseEntity<Object> uploadFiles(
            @RequestParam(value = "uploadTo") String uploadTo,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(s3Service.uploadFiles(uploadTo, multipartFiles));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFile(
            @Valid @RequestParam(value = "from") String from,
            @Valid @RequestParam(value = "url") String url ){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(s3Service.deleteFile(from, url));
    }

}
