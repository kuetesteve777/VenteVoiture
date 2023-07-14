package com.website.ventevoiture.controller;

import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.dto.VoitureDto;
import com.website.ventevoiture.service.VoitureService;
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
@RequestMapping("/voiture")
public class VoitureController {
    
    @Autowired
    private final VoitureService voitureService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestBody VoitureDto voiture) {
       return voitureService.createVoiture(voiture);
    }

        
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestBody VoitureDto voiture){
        return voitureService.updateVoiture(id, voiture);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return voitureService.findVoiture(id);
    }
    
    @GetMapping("/get/name/{name}")
    public ResultDto get(@PathVariable("name") String name) {
        return voitureService.findVoiture(name);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return voitureService.findAllVoiture();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return voitureService.deleteVoiture(id);
    }
    
    @DeleteMapping("/delete/name/{name}")
    public ResultDto  delete(@PathVariable("name") String name){
        return voitureService.deleteVoiture(name);
    }
    
    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return voitureService.deleteAllVoiture();
    }
}
