package com.example.MyBoxYoonho.controller;

import com.example.MyBoxYoonho.service.FileService;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class fileController{
    private final FileService fileService;

    @Autowired
    public fileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public FileUploadDTO uploadfile(@RequestParam("file")MultipartFile file){
        String fileName = fileService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        // file size 얻는 법
        //long fileSize = file.getSize();
        //String fileSizeInKB = String.format("%.2f", (double) fileSize / 1024);

        return new FileUploadDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<FileUploadDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile files){
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadfile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // 파일을 Resource타입으로 받아온다.
        Resource resource = fileService.loadFileAsResource(fileName);

        // 파일 content type 확인 (jpg, png 등..)
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // 파일 타입을 알 수 없는 경우의 기본값
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}