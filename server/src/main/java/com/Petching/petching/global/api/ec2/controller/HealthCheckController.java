package com.Petching.petching.global.api.ec2.controller;

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
