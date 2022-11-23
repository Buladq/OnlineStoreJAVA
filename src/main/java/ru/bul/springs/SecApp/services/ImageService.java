package ru.bul.springs.SecApp.services;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.bul.springs.SecApp.models.Image;
import ru.bul.springs.SecApp.repository.ImageRepository;

import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image findImage(int id){
      return  imageRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Image image){
        imageRepository.save(image);
    }
    @Transactional
    public void deleteIm(int id){
        imageRepository.deleteById(id);
    }


}
