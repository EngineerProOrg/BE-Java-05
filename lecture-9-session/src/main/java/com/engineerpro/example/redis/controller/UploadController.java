package com.engineerpro.example.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.example.redis.dto.UploadImageRequest;
import com.engineerpro.example.redis.dto.UploadImageResponse;
import com.engineerpro.example.redis.service.UploadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/upload")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @PostMapping()
    public ResponseEntity<UploadImageResponse> postMethodName(@Valid @RequestBody UploadImageRequest request) {
        String url = uploadService.uploadImage(request.getBase64ImageString());
        return ResponseEntity.status(HttpStatus.OK).body(UploadImageResponse.builder().url(url).build());
    }

    @GetMapping("/user")
    public Object getPrincipal(Authentication authentication) {
        return authentication.getPrincipal();
    }
}
