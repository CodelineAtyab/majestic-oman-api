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

    public String picLabel;

    public PictureInfo(Integer picID, String picName, String picDes, String picPath,String picLabel) {
        this.picID = picID;
        this.picName = picName;
        this.picDes = picDes;
        this.picPath = picPath;
        this.picLabel =picLabel;
    }

    public  PictureInfo(){}

    public Integer getPicID() {
        return picID;
    }

    public void setPicID(Integer picID) {
        this.picID = picID;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicDes() {
        return picDes;
    }

    public void setPicDes(String picDes) {
        this.picDes = picDes;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicLabel() {
        return picLabel;
    }

    public void setPicLabel(String picLabel) {
        this.picLabel = picLabel;
    }
}
