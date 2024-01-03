package com.majesticoman.api.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PictureInfo {
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
public Integer picID;

public String picName;

public String picDes;

public String picPath;

    public PictureInfo(Integer picID, String picName, String picDes, String picPath) {
        this.picID = picID;
        this.picName = picName;
        this.picDes = picDes;
        this.picPath = picPath;
    }

    public  PictureInfo(){}
}
