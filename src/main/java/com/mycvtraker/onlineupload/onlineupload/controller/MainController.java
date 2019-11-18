package com.mycvtraker.onlineupload.onlineupload.controller;

import com.mycvtraker.onlineupload.onlineupload.service.GoogleDriveAPI;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class MainController {

    private GoogleDriveAPI googleDriveAPI;

    @Autowired
    public void authenticate(GoogleDriveAPI googleDriveAPI){
        this.googleDriveAPI = googleDriveAPI;
    }

    @PostMapping("/authGD")
    public boolean auth(){
        try {
            return googleDriveAPI.auth();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/uploadFile",method = { RequestMethod.POST, RequestMethod.PUT },
            consumes = { "multipart/form-data" })
    public String uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile){
        try {
//            File file = new File(System.getProperty("java.io.tmpdir")+"/"+multipartFile.getOriginalFilename());
//            multipartFile.transferTo(file);

            File file = new File(System.getProperty("java.io.tmpdir")+"/tempFiles", multipartFile.getOriginalFilename());
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

            return googleDriveAPI.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
