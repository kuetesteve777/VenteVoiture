package com.website.ventevoiture.controller;

import com.website.ventevoiture.dto.AdminDto;
import com.website.ventevoiture.dto.LoginDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private final AdminService userService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestBody AdminDto adminDto) {
       return userService.createAdmin(adminDto);
    }

    @PostMapping("/auth")
    public ResultDto authenticate(@RequestBody LoginDto loginRequest) {
        return userService.connectAdmin(loginRequest);
    }
        
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestBody AdminDto adminRequest){
        return userService.updateAdmin(id, adminRequest);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return userService.findAdmin(id);
    }
    
    @GetMapping("/get/name/{name}")
    public ResultDto get(@PathVariable("name") String name) {
        return userService.findAdmin(name);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return userService.findAllAdmin();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return userService.deleteAdmin(id);
    }
    
    @DeleteMapping("/delete/name/{name}")
    public ResultDto  delete(@PathVariable("name") String name){
        return userService.deleteAdmin(name);
    }
    
    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return userService.deleteAllAdmin();
    }
}
