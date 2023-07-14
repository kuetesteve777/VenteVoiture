package com.website.ventevoiture.controller;

import com.website.ventevoiture.dto.ModeleDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.service.ModeleService;
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
@RequestMapping("/modele")
public class ModeleController {
    
    @Autowired
    private final ModeleService modeleService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestBody ModeleDto modeleDto) {
       return modeleService.createModele(modeleDto);
    }
 
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestBody ModeleDto modeleRequest){
        return modeleService.updateModele(id, modeleRequest);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return modeleService.findModele(id);
    }
    
    @GetMapping("/get/name/{name}")
    public ResultDto get(@PathVariable("name") String name) {
        return modeleService.findModele(name);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return modeleService.findAllModele();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return modeleService.deleteModele(id);
    }
    
    @DeleteMapping("/delete/name/{name}")
    public ResultDto  delete(@PathVariable("name") String name){
        return modeleService.deleteModele(name);
    }
    
    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return modeleService.deleteAllModele();
    }
}