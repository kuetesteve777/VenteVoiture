package com.website.ventevoiture.controller;


import com.website.ventevoiture.dto.ClientDto;
import com.website.ventevoiture.dto.LoginDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.service.ClientService;
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


@AllArgsConstructor
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private final ClientService clientService;
    
    @PostMapping("/register")
    public ResultDto create(@RequestBody ClientDto clientDto) {
       return clientService.createClient(clientDto);
    }

    @PostMapping("/auth")
    public ResultDto authenticate(@RequestBody LoginDto loginRequest) {
        return clientService.connectClient(loginRequest);
    }
        
    @PutMapping("/update/{id}")
    public ResultDto update(@PathVariable("id") long id, @RequestBody ClientDto clientRequest){
        return clientService.updateClient(id, clientRequest);
    }

    @GetMapping("/get/id/{id}")
    public ResultDto get(@PathVariable("id") Long id) {
        return clientService.findClient(id);
    }
    
    @GetMapping("/get/name/{name}")
    public ResultDto get(@PathVariable("name") String name) {
        return clientService.findClient(name);
    }
    
    @GetMapping("/get/all")
    public ResultDto getAll() {
        return clientService.findAllClient();
    }
    
    @DeleteMapping("/delete/id/{id}")
    public ResultDto  delete(@PathVariable("id") long id){
        return clientService.deleteClient(id);
    }
    
    @DeleteMapping("/delete/name/{name}")
    public ResultDto  delete(@PathVariable("name") String name){
        return clientService.deleteClient(name);
    }
    
    @DeleteMapping("/delete/all")
    public ResultDto deleteAll(){
        return clientService.deleteAllClient();
    }
}
