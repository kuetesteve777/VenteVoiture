package com.website.ventevoiture.controller;

import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.service.ImageService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kuetesteve21
 */
@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    private final ImageService imageService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestParam("images") List<MultipartFile> images) {
       return imageService.createImage(images);
    }

        
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestParam("image") MultipartFile image){
        return imageService.updateImage(id, image);
    }
    
    @GetMapping("/get/{name}")
    public ResponseEntity<?>  getImageByName(@PathVariable("name") String name){
        
        byte[] image = imageService.getImage(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }
    
    @GetMapping("/get/data/{id}")
    public ResponseEntity<?>  getImageById(@PathVariable("id") long id){
        
        byte[] image = imageService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }
    
    @GetMapping("/song/{name}")
    public ResponseEntity<?> getSongByName(@PathVariable("name") String name){
        byte[] song = null;
        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .body(song);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return imageService.findImage(id);      
    }
    
    @GetMapping("/get/name/{name}")
    public ResultDto get(@PathVariable("name") String name) {
        return imageService.findImage(name);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return imageService.findAllImage();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return imageService.deleteImage(id);
    }
    
    @DeleteMapping("/delete/name/{name}")
    public ResultDto  delete(@PathVariable("name") String name){
        return imageService.deleteImage(name);
    }
    
    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return imageService.deleteAllImage();
    }
}
