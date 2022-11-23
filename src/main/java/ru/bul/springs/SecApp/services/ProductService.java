package ru.bul.springs.SecApp.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.bul.springs.SecApp.models.Image;
import ru.bul.springs.SecApp.models.Person;
import ru.bul.springs.SecApp.models.Product;
import ru.bul.springs.SecApp.repository.ImageRepository;
import ru.bul.springs.SecApp.repository.ProductRepository;
import ru.bul.springs.SecApp.util.ProductComparator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ImageRepository imageRepository;


    public ProductService(ProductRepository productRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

   public List<Product> findAllProducts(boolean exp,boolean che){
        if(che){
            return productRepository.findAll(Sort.by("price"));//первый вариант сортировки
        }
        else if(exp){
            List<Product> pp=productRepository.findAll();
            pp.sort(new ProductComparator());
            return pp;//второй вариант сортировки
        }
        return productRepository.findAll();
    }





    @Transactional
    public void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
            imageRepository.save(image1);

        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
            imageRepository.save(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
            imageRepository.save(image3);
        }
       product.setPreviewImageId((long) product.getImages().get(0).getId());
        productRepository.save(product);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    public List<Product> findByTitle(String title){
        return productRepository.findBytitle(title);
    }

    public Optional<Product> getProductById(int id){
        return productRepository.findById(id);
    }

    @Transactional
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }


    @Transactional
    public void updateProduct(int id,Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
       Product product1=getProductById(id).get();

       List<Image> imageslist=product1.getImages();
        for (var im:
             imageslist) {
            imageRepository.deleteById(im.getId());
        }
        product.setId(id);
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
            imageRepository.save(image1);

        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
            imageRepository.save(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
            imageRepository.save(image3);
        }

        product.setPreviewImageId((long) product.getImages().get(0).getId());
        productRepository.save(product);
    }

}
