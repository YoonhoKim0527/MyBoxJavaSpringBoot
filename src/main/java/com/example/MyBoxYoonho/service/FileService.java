package com.example.MyBoxYoonho.service;

import com.example.MyBoxYoonho.domain.File;
import com.example.MyBoxYoonho.exception.FileNotFoundException;
import com.example.MyBoxYoonho.exception.FileStorageException;
import com.example.MyBoxYoonho.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    private final Environment env;
    private final Path fileLocation;
    private final FileRepository fileRepository;


    @Autowired
    public FileService(Environment env, FileRepository fileRepository) {
        this.env = env;
        this.fileLocation = Paths.get(Objects.requireNonNull(env.getProperty("file.uploadDir"))).toAbsolutePath().normalize();
        this.fileRepository = fileRepository;
        try{
            Files.createDirectories(this.fileLocation);
        }catch(Exception ex){
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            File fileEntity = File.builder()
                    .uuidName(UUID.randomUUID().toString())
                    .fileSize(file.getSize())
                    .filePath(targetLocation.toString())
                    .realName(FilenameUtils.removeExtension(fileName))
                    .extension(FilenameUtils.getExtension(fileName))
                    .build();

            fileRepository.save(fileEntity);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }
}
