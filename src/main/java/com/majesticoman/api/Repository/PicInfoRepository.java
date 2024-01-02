package com.majesticoman.api.Repository;

import com.majesticoman.api.Models.PictureInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PicInfoRepository extends JpaRepository<PictureInfo, Integer> {
}
