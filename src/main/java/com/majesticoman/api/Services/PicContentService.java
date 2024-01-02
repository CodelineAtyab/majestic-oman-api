package com.majesticoman.api.Services;

import com.majesticoman.api.Models.PictureInfo;
import com.majesticoman.api.Repository.PicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PicContentService {

    @Autowired
    public PicInfoRepository picInfoRepository;

    public PictureInfo createNewPic(PictureInfo pictureInfo){
        picInfoRepository.save(pictureInfo);
        return pictureInfo;
    }
}
