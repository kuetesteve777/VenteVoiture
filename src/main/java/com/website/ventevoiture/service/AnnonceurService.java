package com.website.ventevoiture.service;

import com.website.ventevoiture.dto.AnnonceurDto;
import com.website.ventevoiture.dto.LoginDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.model.Annonceur;
import com.website.ventevoiture.repository.AnnonceurRepository;
import jakarta.transaction.Transactional;
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

/**
 *
 * @author kuetesteve21
 */

@Data
@Component
public class AnnonceurService {
      
    @Autowired
    private AnnonceurRepository annonceurRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;
    
    private MethodArgumentNotValidException m;
    
    @Transactional
    public ResultDto createAnnonceur(@Valid  AnnonceurDto annonceurDto) {
        
        Annonceur annonceurRequest = modelMapper.map(annonceurDto, Annonceur.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<Annonceur>> violations = validator.validate(annonceurRequest);

        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Annonceur> violation : violations) {
                errors.add(violation.getMessage());
            }
            return new ResultDto(false,"Failed to create annonceur !" ,null,HttpStatus.BAD_REQUEST.value(), errors);
        }
        
        Annonceur email = annonceurRepository.findByEmail(annonceurDto.getEmail()).orElse(null);
        if(email!=null) return new ResultDto(false,"Failed to create annonceur : specified email is already taken !" ,null,HttpStatus.BAD_REQUEST.value(), null);
     
        Annonceur phone = annonceurRepository.findByPhone(annonceurDto.getPhone()).orElse(null);
        if(phone!=null) return new ResultDto(false,"Failed to create annonceur : specified phone number is already taken !" ,null,HttpStatus.BAD_REQUEST.value(), null);
        
        Annonceur adminCreated = annonceurRepository.save(annonceurRequest);
        return new ResultDto(true,"Annonceur successfully created !" ,adminCreated,HttpStatus.CREATED.value(), null);
    }
    
    @Transactional
    public ResultDto updateAnnonceur(long id, AnnonceurDto annonceurRequest){
        
        ResultDto resultDto;
        Annonceur annonceur = annonceurRepository.findById(id).orElse(null);
        if(annonceur==null) resultDto = new ResultDto(false, "Failed to update : annonceur to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            annonceur.setName(annonceurRequest.getName());
            annonceur.setUserName(annonceur.getUserName());
            annonceur.setEmail(annonceurRequest.getEmail());
            annonceur.setPassword(annonceurRequest.getPassword());
            annonceur.setPhone(annonceurRequest.getPhone());
            annonceur.setAdress(annonceurRequest.getAdress());
            Annonceur annonceurUpdated = annonceurRepository.saveAndFlush(annonceur);
            resultDto = new ResultDto(true, "Admin updated successfully !", annonceurUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }

    @Transactional
    public ResultDto connectAnnonceur(LoginDto loginRequest)  {
        
        Annonceur annonceurFound = annonceurRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()).orElse(null);
        Annonceur email = annonceurRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        Annonceur password = annonceurRepository.findByPassword(loginRequest.getPassword()).orElse(null);
        if(email==null)
            return new ResultDto(false,"Incorrect email" ,email,HttpStatus.NOT_ACCEPTABLE.value(), null);
        else if(password==null)
            return new ResultDto(false,"Incorrect password" ,password,HttpStatus.NOT_ACCEPTABLE.value(), null);      
        else{
            annonceurFound.setConnected(true);
            return new ResultDto(true,"Annonceur logged in successfully !" , annonceurFound, HttpStatus.ACCEPTED.value(), null);
        }
    }
    
    @Transactional
    public ResultDto findAnnonceur(Long id){
        
        Annonceur annonceur = annonceurRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Annonceur found !" , annonceur ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Annonceur not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return annonceur == null ? failed : sucess;
    }
    
    @Transactional
    public ResultDto findAnnonceur(String name){
        
        Annonceur annonceur = annonceurRepository.findByName(name).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Annonceur found !" , annonceur ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Annonceur not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return annonceur == null ? failed : sucess;
    }
    
    @Transactional
    public ResultDto findAllAnnonceur() {
        
        List<Annonceur> annonceurFoundList = annonceurRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of annonceur !" , annonceurFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return annonceurFoundList.isEmpty() ? failed : sucess;
    }
    
    @Transactional
    public ResultDto deleteAnnonceur(Long id){
        
        Annonceur annonceur = annonceurRepository.findById(id).orElse(null);
        if(annonceur==null) return new ResultDto(false,"Deleting failed : Annonceur not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        annonceurRepository.delete(annonceur);
        return new ResultDto(true,"Deleted successfull" ,annonceur,HttpStatus.OK.value(), null);
    }
    
    @Transactional
    public ResultDto deleteAnnonceur(String name){
        
        Annonceur annonceur = annonceurRepository.findByName(name).orElse(null);
        if(annonceur==null) return new ResultDto(false,"Deleting failed : annonceur not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        annonceurRepository.delete(annonceur);
        return new ResultDto(true,"Deleted successfull" ,annonceur,HttpStatus.OK.value(), null);
    }
    
    @Transactional
    public ResultDto deleteAllAnnonceur(){
        
        List<Annonceur> annonceurFoundList = annonceurRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of annonceur deleted !", annonceurFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no annonceur found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(annonceurFoundList.isEmpty()) result = failed;
        else {
            annonceurRepository.deleteAll();
            result = success;
        }
        return result;
    }
    
}
