package com.website.ventevoiture.controller;

import com.website.ventevoiture.dto.AnnonceDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.service.AnnonceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kuetesteve21
 */

@AllArgsConstructor
@RestController
@RequestMapping("/annonce")
public class AnnonceController {
    
    @Autowired
    private final AnnonceService annonceService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestBody AnnonceDto annonceDto) {
       return annonceService.createAnnonce(annonceDto);
    }
        
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestBody AnnonceDto annonceRequest){
        return annonceService.updateAnnonce(id, annonceRequest);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return annonceService.findAnnonce(id);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return annonceService.findAllAnnonce();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return annonceService.deleteAnnonce(id);
    }

    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return annonceService.deleteAllAnnonce();
    }
}
