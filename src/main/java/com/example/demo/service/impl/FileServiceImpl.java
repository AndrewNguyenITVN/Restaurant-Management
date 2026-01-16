package com.example.demo.service.impl;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("${FileUpload.rootPath}")
    private String rootPath;
    private Path root;

    private void init(){
        try{
            root = Paths.get(rootPath);
            if(Files.notExists(root)){
                Files.createDirectories(root);}

        }catch(Exception e){
            System.out.println("Error create " + e.getMessage() );
        }
    }

    @Override
    public boolean savefile(MultipartFile file) {
        init();
        try{
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception e){
            System.out.println("Error save " + e.getMessage() );
            return false;        }

    }

    @Override
    public Resource loadFile(String filename) {
        init();
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }

        }catch (Exception e){
            System.out.println("Error load " + e.getMessage() );
        }
        return null;
    }
}
