package com.website.ventevoiture.service;

import com.website.ventevoiture.dto.ModeleDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.model.Modele;
import com.website.ventevoiture.repository.ModeleRepository;
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
public class ModeleService {
      
    @Autowired
    private ModeleRepository modeleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;
    
    private MethodArgumentNotValidException m;
    
    public ResultDto createModele(@Valid  ModeleDto modeleDto) {
        
        Modele modeleRequest = modelMapper.map(modeleDto, Modele.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<Modele>> violations = validator.validate(modeleRequest);

        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Modele> violation : violations) {
                errors.add(violation.getMessage());
            }
            return new ResultDto(false,"Failed to create modele !" ,null,HttpStatus.BAD_REQUEST.value(), errors);
        }
     
        Modele modeleCreated = modeleRepository.save(modeleRequest);
        return new ResultDto(true,"Modele successfully created !" ,modeleCreated,HttpStatus.CREATED.value(), null);
    }
    
    public ResultDto updateModele(long id, ModeleDto modeleRequest){
        
        ResultDto resultDto;
        Modele modele = modeleRepository.findById(id).orElse(null);
        if(modele==null) resultDto = new ResultDto(false, "Failed to update : modele to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            modele.setName(modeleRequest.getName());
            Modele modeleUpdated = modeleRepository.saveAndFlush(modele);
            resultDto = new ResultDto(true, "Modele updated successfully !", modeleUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }
    
    public ResultDto findModele(Long id){
        
        Modele modele = modeleRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Modele found !" , modele ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Modele not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return modele == null ? failed : sucess;
    }
    
    public ResultDto findModele(String name){
        
        Modele modele = modeleRepository.findByName(name).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Annonceur found !" , modele ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Annonceur not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return modele == null ? failed : sucess;
    }
    
    public ResultDto findAllModele() {
        
        List<Modele> modeleFoundList = modeleRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of modele !" , modeleFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return modeleFoundList.isEmpty() ? failed : sucess;
    }
    
    public ResultDto deleteModele(Long id){
        
        Modele modele = modeleRepository.findById(id).orElse(null);
        if(modele==null) return new ResultDto(false,"Deleting failed : modele not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        modeleRepository.delete(modele);
        return new ResultDto(true,"Deleted successfull" ,modele,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteModele(String name){
        
        Modele modele = modeleRepository.findByName(name).orElse(null);
        if(modele==null) return new ResultDto(false,"Deleting failed : modele not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        modeleRepository.delete(modele);
        return new ResultDto(true,"Deleted successfull" ,modele,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteAllModele(){
        
        List<Modele> modeleFoundList = modeleRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of modele deleted !", modeleFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no modele found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(modeleFoundList.isEmpty()) result = failed;
        else {
            modeleRepository.deleteAll();
            result = success;
        }
        return result;
    }
    
}
