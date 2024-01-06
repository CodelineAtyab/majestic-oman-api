package com.majesticoman.api.Services;
import com.majesticoman.api.Models.PictureInfo;
import com.majesticoman.api.Repository.PicInfoRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


@Service
public class PicContentService {

    @Autowired
    public PicInfoRepository picInfoRepository;


    public ResponseEntity<String> addNewPicture(MultipartFile file, String picName) throws IOException {
        // Save the file to another directory (C:\\Java_Practices\\majestic-oman-api\\pictures)
        File fileDir = new File("C:\\Java_Practices\\majestic-oman-api\\pictures");
        File fileDestinationInDir = new File(fileDir, picName + ".jpg");
        FileUtils.copyInputStreamToFile(file.getInputStream(), fileDestinationInDir);
        //below code for save details of picture in database
        PictureInfo pictureInfo = new PictureInfo(0, picName, null, fileDir.getPath());
        picInfoRepository.save(pictureInfo);

        return ResponseEntity.ok("New Picture Saved");
    }

    public PictureInfo getPicInfo(int id){
        PictureInfo pictureInfo=null;
        Optional<PictureInfo> getPicture=picInfoRepository.findAll().stream().filter((pic)->{
            return (pic.picID==id);
        }).findFirst();
        if (getPicture.isPresent()){
            pictureInfo=getPicture.get();
        }
        return pictureInfo;
    }

    public ResponseEntity<byte[]> getPicture(int id) throws IOException {
        PictureInfo picInfo = getPicInfo(id);
        String picName=picInfo.picName;
        File fileDir = new File("C:\\Java_Practices\\majestic-oman-api\\pictures");
        File fileDestinationInDir = new File(fileDir, picName + ".jpg");
        byte[] picture=FileUtils.readFileToByteArray(fileDestinationInDir);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(picture);

    }

    public ResponseEntity<String> deletePic(int id) throws IOException {
        PictureInfo pic=getPicInfo(id);
        String picName=pic.picName;
        File fileDir = new File("C:\\Java_Practices\\majestic-oman-api\\pictures");
        File fileDestinationInDir = new File(fileDir, picName + ".jpg");
        FileUtils.forceDelete(fileDestinationInDir);
        picInfoRepository.delete(pic);

        return ResponseEntity.ok("Picture has deleted");
    }

    public PictureInfo updatePic(int id,String picName, String descriptionPic){
        PictureInfo pictureInfo=getPicInfo(id);
        pictureInfo.picName=picName;
        pictureInfo.picDes=descriptionPic;

        return picInfoRepository.save(pictureInfo);
    }
}
