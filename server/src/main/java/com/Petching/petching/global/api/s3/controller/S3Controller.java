package com.Petching.petching.global.api.s3.controller;

import com.Petching.petching.global.aws.s3.dto.S3FileDto;
import com.Petching.petching.global.aws.s3.service.S3Service;
import com.Petching.petching.response.SingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;


    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/uploads")
    public ResponseEntity uploadFiles(
            @RequestParam(value = "uploadTo") String uploadTo,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles) {

        List<S3FileDto> multipartFileList = s3Service.uploadFiles(uploadTo, multipartFiles);

        return new ResponseEntity(new SingleResponse<>(multipartFileList), HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Map<String , String>> deleteFile(
            @Valid @RequestParam(value = "from") String from,
            @Valid @RequestParam(value = "url") String url ){

        String result = s3Service.deleteFile(from, url);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
