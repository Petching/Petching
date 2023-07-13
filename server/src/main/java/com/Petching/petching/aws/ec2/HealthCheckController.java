package com.Petching.petching.aws.ec2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping(value = "/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok().build();
    }
}
