package com.website.ventevoiture.controller;

import com.website.ventevoiture.dto.MarqueDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.service.MarqueService;
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
@RequestMapping("/marque")
public class MarqueController {
    
    @Autowired
    private final MarqueService marqueService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestBody MarqueDto marqueDto) {
       return marqueService.createMarque(marqueDto);
    }
 
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestBody MarqueDto marqueRequest){
        return marqueService.updateMarque(id, marqueRequest);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return marqueService.findMarque(id);
    }
    
    @GetMapping("/get/name/{name}")
    public ResultDto get(@PathVariable("name") String name) {
        return marqueService.findMarque(name);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return marqueService.findAllMarque();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return marqueService.deleteMarque(id);
    }
    
    @DeleteMapping("/delete/name/{name}")
    public ResultDto  delete(@PathVariable("name") String name){
        return marqueService.deleteMarque(name);
    }
    
    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return marqueService.deleteAllMarque();
    }
}