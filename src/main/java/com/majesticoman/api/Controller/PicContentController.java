package com.majesticoman.api.Controller;


import com.majesticoman.api.Models.PictureInfo;
import com.majesticoman.api.Repository.PicInfoRepository;
import com.majesticoman.api.Services.PicContentService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/PicturesContent")
public class PicContentController {

    @Autowired
    public PicContentService picContentService;

    @Autowired
    public PicInfoRepository picInfoRepository;
    @GetMapping(path = "/{id}")
    public ResponseEntity<Byte[]> getPicture(){
        return null;}

    @PostMapping
    public ResponseEntity<String> addNewPicture(@RequestParam MultipartFile file, String picName) throws IOException {
        // Save the file to another directory (C:\\Java_Practices\\majestic-oman-api\\pictures)
        File fileDir = new File("C:\\Java_Practices\\majestic-oman-api\\pictures");
        File fileDestinationInDir = new File(fileDir, picName + ".jpg");
        FileUtils.copyInputStreamToFile(file.getInputStream(), fileDestinationInDir);
        //below code for save details of picture in database
        PictureInfo pictureInfo = new PictureInfo(0, picName, null, fileDir.getPath());
        picContentService.createNewPic(pictureInfo);
        return ResponseEntity.ok("New Picture Saved");
    }



}
