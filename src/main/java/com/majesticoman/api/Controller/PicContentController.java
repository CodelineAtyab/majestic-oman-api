package com.majesticoman.api.Controller;


import com.majesticoman.api.Models.PictureInfo;
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
    @GetMapping(path = "/{ID}")
    public ResponseEntity<Byte[]> getPicture(){return null;}

    @PostMapping
    public ResponseEntity<String> addNewPicture(@RequestParam MultipartFile file, String picName) throws IOException {
        // Save the individual file to a specific directory
        File fileDestination = new File("./data/" + picName + ".jpg");
        FileUtils.copyInputStreamToFile(file.getInputStream(), fileDestination);

        // Save the file to another directory (C:\\Java_Practices\\majestic-oman-api\\pictures)
        File fileDir = new File("C:\\Java_Practices\\majestic-oman-api\\pictures");
        File fileDestinationInDir = new File(fileDir, picName + ".jpg");
        FileUtils.copyInputStreamToFile(file.getInputStream(), fileDestinationInDir);


        PictureInfo pictureInfo = new PictureInfo(0, picName, null, fileDir.toString());
        picContentService.createNewPic(pictureInfo);
        return ResponseEntity.ok("New Picture Saved");
    }






}
