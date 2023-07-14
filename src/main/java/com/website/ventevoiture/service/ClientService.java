package com.website.ventevoiture.service;

import com.website.ventevoiture.config.ValidationExceptionHandler;
import com.website.ventevoiture.dto.ClientDto;
import com.website.ventevoiture.dto.LoginDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.model.Client;
import com.website.ventevoiture.repository.ClientRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;


@Data
@Component
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    @Autowired
    private ValidationExceptionHandler validationExceptionHandler;

    private MethodArgumentNotValidException m;
    
    public ResultDto createClient(@Valid  ClientDto clientDto) {
        
        Client clientRequest = modelMapper.map(clientDto, Client.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<Client>> violations = validator.validate(clientRequest);

        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Client> violation : violations) {
                errors.add(violation.getMessage());
            }
            return new ResultDto(false,"Failed to create client !" ,null,HttpStatus.BAD_REQUEST.value(), errors);
        }

        Client clientCreated = clientRepository.save(clientRequest);
        return new ResultDto(true,"Client successfully created !" ,clientCreated,HttpStatus.CREATED.value(), null);
    }
    
    public ResultDto updateClient(long id, ClientDto clientRequest){
        
        ResultDto resultDto;
        Client client = clientRepository.findById(id).orElse(null);
        if(client==null) resultDto = new ResultDto(false, "Failed to update : client to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            client.setName(clientRequest.getName());
            client.setUserName(clientRequest.getUserName());
            client.setEmail(clientRequest.getEmail());
            client.setPassword(clientRequest.getPassword());
            Client clientUpdated = clientRepository.saveAndFlush(client);
            resultDto = new ResultDto(true, "Client updated successfully !", clientUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }

    public ResultDto connectClient(LoginDto loginRequest)  {
        
        
        Client email = clientRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        Client password = clientRepository.findByPassword(loginRequest.getPassword()).orElse(null);
        if(email==null)
            return new ResultDto(false,"Incorrect email" ,email,HttpStatus.NOT_ACCEPTABLE.value(), null);
        else if(password==null)
            return new ResultDto(false,"Incorrect password" ,password,HttpStatus.NOT_ACCEPTABLE.value(), null);      
        else{
            email.setConnected(true);
            return new ResultDto(true,"Client logged in successfully !" , email, HttpStatus.ACCEPTED.value(), null);
        }
            
    }
    
    public ResultDto findClient(Long id){
        
        Client client = clientRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Client found !" , client ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Client not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return client == null ? failed : sucess;
    }
    
    public ResultDto findClient(String name){
        
        Client client = clientRepository.findByName(name).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Client found !" , client ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Client not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return client == null ? failed : sucess;
    }
    
    public ResultDto findAllClient() {
        
        List<Client> clientFoundList = clientRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of clients !" , clientFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return clientFoundList.isEmpty() ? failed : sucess;
    }
    
    public ResultDto deleteClient(Long id){
        
        Client client = clientRepository.findById(id).orElse(null);
        if(client==null) return new ResultDto(false,"Deleting failed : client not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        clientRepository.delete(client);
        return new ResultDto(true,"Deleted successfull" ,client,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteClient(String name){
        
        Client client = clientRepository.findByName(name).orElse(null);
        if(client==null) return new ResultDto(false,"Deleting failed : client not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        clientRepository.delete(client);
        return new ResultDto(true,"Deleted successfull" ,client,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteAllClient(){
        
        List<Client> clientFoundList = clientRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of client deleted !", clientFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no client found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(clientFoundList.isEmpty()) result = failed;
        else {
            clientRepository.deleteAll();
            result = success;
        }
        return result;
    }
}
