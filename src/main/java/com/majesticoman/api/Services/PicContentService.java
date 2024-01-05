package com.majesticoman.api.Services;
import com.majesticoman.api.Models.PictureInfo;
import com.majesticoman.api.Repository.PicInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Service
public class PicContentService {

    @Autowired
    public PicInfoRepository picInfoRepository;
    private String lastImagePath = null;
//    public PictureInfo createNewPic(PictureInfo pictureInfo){
//        picInfoRepository.save(pictureInfo);
//        return pictureInfo;
//    }
    public PictureInfo uploadImage(MultipartFile file) throws IOException {
        PictureInfo imageInfo = new PictureInfo();
        imageInfo = picInfoRepository.save(imageInfo);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = "image_" + imageInfo.getPicID()+ "." + extension;
        File destinationFile = new File("./Data/" + newFileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), destinationFile);

        imageInfo.setPicPath(destinationFile.getPath());
        return picInfoRepository.save(imageInfo);
    }
    public File retrieveImageFile(String id) {
        String[] possibleExtensions = {"png", "jpg", "jpeg"};

        return Arrays.stream(possibleExtensions)
                .map(ext -> Paths.get("./Data/image_" + id + "." + ext).toFile())
                .filter(File::exists)
                .findFirst()
                .orElse(null);
    }
    public File getRandomImage() throws IOException {
        Path dir = Paths.get("./Data/");
        List<File> imageFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{png,jpg,jpeg}")) {
            for (Path entry : stream) {
                if (lastImagePath == null || !entry.toString().equals(lastImagePath)) {
                    imageFiles.add(entry.toFile());
                }
            }
        }

        if (imageFiles.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(imageFiles.size());
        File selectedFile = imageFiles.get(randomIndex);

        // Update lastImagePath
        lastImagePath = selectedFile.getPath();

        return selectedFile;
    }
    public MediaType getMediaType(File file) {
        MimeTypes mimeTypes = TikaConfig.getDefaultConfig().getMimeRepository();
        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            org.apache.tika.mime.MediaType tikaMediaType = mimeTypes.detect(input, new Metadata());
            return MediaType.parseMediaType(tikaMediaType.toString());
        } catch (IOException e) {
            System.out.println("Error detecting MIME type: " + e.getMessage());
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public void UpdateImage(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {

        String[] possibleExtensions = {"png", "jpg", "jpeg"};


        Arrays.stream(possibleExtensions)
                .map(ext -> new File("./Data/image_" + id + "." + ext))
                .filter(File::exists)
                .forEach(File::delete);


        String newExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        File destinationFile = new File("./Data/image_" + id + "." + newExtension);
        FileUtils.copyInputStreamToFile(file.getInputStream(), destinationFile);


    }

    public void deleteImage(int id) throws IOException {
        String[] possibleExtensions = {"png", "jpg", "jpeg"};
        boolean fileDeleted = Arrays.stream(possibleExtensions)
                .map(ext -> new File("./Data/image_" + id + "." + ext))
                .filter(File::exists)
                .findFirst()
                .map(File::delete)
                .orElse(false);

        if (picInfoRepository.existsById(id)) {
            picInfoRepository.deleteById(id);
        } else {
            if (!fileDeleted) {
                throw new EntityNotFoundException("Image not found with ID: " + id);
            }
        }
    }
    public List<PictureInfo> getAllImages() {
        return picInfoRepository.findAll();
    }

    public PictureInfo getImageById(int id) {
        return picInfoRepository.findById(id).orElse(null);
    }

    public void updateImageinfo(int id, PictureInfo img) {
        PictureInfo existingImage = picInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with ID: " + id));

        existingImage.setPicDes(img.getPicDes());
        existingImage.setPicPath(img.getPicPath());
        existingImage.setPicLable(img.getPicLable());
        existingImage.setPicName(img.getPicName());
        picInfoRepository.save(existingImage);
    }

    public void deleteImageinfo(int id) {
        if (!picInfoRepository.existsById(id)) {
            throw new EntityNotFoundException("Image not found with ID: " + id);
        }
        picInfoRepository.deleteById(id);
    }


}
