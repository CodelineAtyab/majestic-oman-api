package com.majesticoman.api.Services;

import com.majesticoman.api.Controller.PicContentController;
import com.majesticoman.api.Models.PictureInfo;
import com.majesticoman.api.Repository.PicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class PicContentService {

    @Autowired
    public PicInfoRepository picInfoRepository;


    public PictureInfo createNewPic(PictureInfo pictureInfo){
        picInfoRepository.save(pictureInfo);
        return pictureInfo;
    }


}
