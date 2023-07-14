package com.website.ventevoiture.service;

import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.model.Image;
import com.website.ventevoiture.repository.ImageRepository;
import com.website.ventevoiture.util.ImageUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kuetesteve21
 */

@Data
@Component
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;
    
    private MethodArgumentNotValidException m;
    
    @Transactional
    public ResultDto createImage(List<MultipartFile> images) {
        
        List<Image> imageRequestList = new ArrayList<>();
        Image imageRequest;
        for(MultipartFile file:images){
            try {
                imageRequest = Image.builder().name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imgData(ImageUtil.compressImage(file.getBytes())).build();
                imageRequestList.add(imageRequest);
            } catch (IOException ex) {}
        }
        List<Image> imageCreatedList = imageRepository.saveAll(imageRequestList);
        return new ResultDto(true,"image(s) successfully created !" ,imageCreatedList,HttpStatus.CREATED.value(), null);
    }
    
    @Transactional
    public ResultDto updateImage(long id, MultipartFile imageRequest){
        
        ResultDto resultDto;
        Image image = imageRepository.findById(id).orElse(null);
        if(image==null) resultDto = new ResultDto(false, "Failed to update : image to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            image.setName(imageRequest.getOriginalFilename());
            image.setType(imageRequest.getContentType());
            try{ image.setImgData(ImageUtil.compressImage(imageRequest.getBytes()));}
            catch(IOException ex){}
            Image imageUpdated = imageRepository.saveAndFlush(image);
            resultDto = new ResultDto(true, "Image updated successfully !", imageUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }
    
    @Transactional
    public byte[] getImage(String name) {
        Image dbImage = imageRepository.findByName(name).orElse(null);
        byte[] image;
        if(dbImage!=null) image = ImageUtil.decompressImage(dbImage.getImgData());
        else image = new byte[1000*1000];
        return image;
    }
    
    @Transactional
    public byte[] getImage(long id) {
        Image dbImage = imageRepository.findById(id).orElse(null);
        byte[] image;
        if(dbImage!=null) image = ImageUtil.decompressImage(dbImage.getImgData());
        else image = new byte[1000*1000];
        return image;
    }
    
    @Transactional
    public ResultDto findImage(Long id){
        
        Image image = imageRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Image found !" , image ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false,"Image not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return image == null ? failed : sucess;
    }
    
    @Transactional
    public ResultDto findImage(String name){
        
        Image image = imageRepository.findByName(name).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Image found !" , image ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false,"Image not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return image == null ? failed : sucess;
    }
    
    @Transactional
    public ResultDto findAllImage() {
        
        List<Image> imageFoundList = imageRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of image !" , imageFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return imageFoundList.isEmpty() ? failed : sucess;
    }
    
    @Transactional
    public ResultDto deleteImage(Long id){
        
        Image image = imageRepository.findById(id).orElse(null);
        if(image==null) return new ResultDto(false,"Deleting failed : Image not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        imageRepository.delete(image);
        return new ResultDto(true,"Deleted successfull" ,image,HttpStatus.OK.value(), null);
    }
    
    @Transactional
    public ResultDto deleteImage(String name){
        
        Image image = imageRepository.findByName(name).orElse(null);
        if(image==null) return new ResultDto(false,"Deleting failed : image not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        imageRepository.delete(image);
        return new ResultDto(true,"Deleted successfull" ,image,HttpStatus.OK.value(), null);
    }
    
    @Transactional
    public ResultDto deleteAllImage(){
        
        List<Image> imageFoundList = imageRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of image deleted !", imageFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no image found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(imageFoundList.isEmpty()) result = failed;
        else {
            imageRepository.deleteAll();
            result = success;
        }
        return result;
    }
    
}
