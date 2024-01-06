package com.majesticoman.api.Controller;


import com.majesticoman.api.Models.PictureInfo;
import com.majesticoman.api.Repository.PicInfoRepository;
import com.majesticoman.api.Services.PicContentService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPicture(@PathVariable int id) throws IOException {
        return picContentService.getPicture(id);
    }

    @PostMapping
    public ResponseEntity<String> addNewPicture(@RequestParam MultipartFile file, String picName) throws IOException {

        return picContentService.addNewPicture(file,picName);
    }

    @GetMapping("/PictureInfo/{id}")
    public PictureInfo getPictureInfo(@PathVariable int id){
        PictureInfo getPicInfo=picContentService.getPicInfo(id);
        return getPicInfo;
    }

    @PutMapping("/{id}")
    public PictureInfo updatePic(@PathVariable int id, @RequestParam String picName, @RequestParam String descriptionPic){
        return picContentService.updatePic(id,picName, descriptionPic);
    }

    public ResponseEntity<String> deletePic(@PathVariable int id) throws IOException {
        return picContentService.deletePic(id);
    }
}
