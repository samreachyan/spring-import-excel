package com.sakcode.consumer.rest;

import com.sakcode.consumer.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/mPay")
@Slf4j
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @GetMapping()
    public ResponseEntity<?> getWelcome() {
        return ResponseEntity.ok().body("Ok");
    }

    @GetMapping("/ISP")
    public CompletableFuture<ResponseEntity> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam("numberOfSheet") Integer numberOfSheet) {

        System.out.println(numberOfSheet);
        uploadService.upload(file, numberOfSheet);

        return CompletableFuture.completedFuture("Something uploaded ").thenApply(ResponseEntity::ok);
    }
}
