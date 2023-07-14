package com.website.ventevoiture.controller;

import com.website.ventevoiture.dto.AnnonceurDto;
import com.website.ventevoiture.dto.LoginDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.service.AnnonceurService;
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
@RequestMapping("/annonceur")
public class AnnonceurController {
    
    @Autowired
    private final AnnonceurService annonceurService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestBody AnnonceurDto annonceurDto) {
       return annonceurService.createAnnonceur(annonceurDto);
    }

    @PostMapping("/auth")
    public ResultDto authenticate(@RequestBody LoginDto loginRequest) {
        return annonceurService.connectAnnonceur(loginRequest);
    }
        
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestBody AnnonceurDto annonceurRequest){
        return annonceurService.updateAnnonceur(id, annonceurRequest);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return annonceurService.findAnnonceur(id);
    }
    
    @GetMapping("/get/name/{name}")
    public ResultDto get(@PathVariable("name") String name) {
        return annonceurService.findAnnonceur(name);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return annonceurService.findAllAnnonceur();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return annonceurService.deleteAnnonceur(id);
    }
    
    @DeleteMapping("/delete/name/{name}")
    public ResultDto  delete(@PathVariable("name") String name){
        return annonceurService.deleteAnnonceur(name);
    }
    
    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return annonceurService.deleteAllAnnonceur();
    }
}
