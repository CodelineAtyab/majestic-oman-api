package com.majesticoman.api.Controller;


import com.majesticoman.api.Models.PictureInfo;
import com.majesticoman.api.Repository.PicInfoRepository;
import com.majesticoman.api.Services.PicContentService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FileUtils;
import org.apache.tika.metadata.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/picturesContent")
public class PicContentController {

    @Autowired
    public PicContentService picContentService;

    @Autowired
    public PicInfoRepository picInfoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> showImage(@PathVariable String id) throws IOException {
        File file = picContentService.retrieveImageFile(id);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileContent = FileUtils.readFileToByteArray(file);
        MediaType mediaType = picContentService.getMediaType(file);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .body(fileContent);
    }

    @GetMapping("/random")
    public ResponseEntity<byte[]> getRandomImage() {
        try {
            File randomImageFile = picContentService.getRandomImage();

            if (randomImageFile == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileContent = FileUtils.readFileToByteArray(randomImageFile);
            MediaType mediaType = picContentService.getMediaType(randomImageFile);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + randomImageFile.getName() + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving random image".getBytes());
        }
    }

    @PostMapping()
    public ResponseEntity<?> UploadImage(@RequestParam("file") MultipartFile file) {
        try {
            PictureInfo uploadedImage = picContentService.uploadImage(file);
            return ResponseEntity.ok(uploadedImage.getPicID());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            picContentService.UpdateImage(id, file);
            return ResponseEntity.ok("File updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating file");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteImage(@PathVariable int id) {
        try {
            picContentService.deleteImage(id);
            return ResponseEntity.ok("File and database record deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during file deletion");
        }
    }

    @GetMapping("/data")
    public ResponseEntity<List<PictureInfo>> getAllImages() {
        List<PictureInfo> images = picContentService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<PictureInfo> getImageById(@PathVariable int id) {
        PictureInfo image = picContentService.getImageById(id);
        if (image != null) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/data/{id}")
    public ResponseEntity<String> updateImage(@PathVariable int id, @RequestBody PictureInfo img) {
        try {
            picContentService.updateImageinfo(id, img);
            return ResponseEntity.ok("Image info updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable int id) {
        try {
            picContentService.deleteImageinfo(id);
            return ResponseEntity.ok("Image successfully deleted.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
