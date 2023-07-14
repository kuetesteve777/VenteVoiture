package com.website.ventevoiture.service;

import com.website.ventevoiture.dto.MarqueDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.model.Marque;
import com.website.ventevoiture.model.Modele;
import com.website.ventevoiture.repository.MarqueRepository;
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
public class MarqueService {
      
    @Autowired
    private MarqueRepository marqueRepository;
    
    @Autowired ModeleRepository modeleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;
    
    private MethodArgumentNotValidException m;
    
    public ResultDto createMarque(@Valid  MarqueDto marqueDto) {
        
        Marque marqueRequest = modelMapper.map(marqueDto, Marque.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<Marque>> violations = validator.validate(marqueRequest);

        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Marque> violation : violations) {
                errors.add(violation.getMessage());
            }
            return new ResultDto(false,"Failed to create marque !" ,null,HttpStatus.BAD_REQUEST.value(), errors);
        }
        
        Modele modele = modeleRepository.findByName(marqueDto.getModel()).orElse(null);
        if(modele==null) new ResultDto(false,"Failed to create marque : Specified modele doesn't exit !" ,null,HttpStatus.BAD_REQUEST.value(), null);
        else marqueRequest.getModeles().add(modele);
        
        Marque marqueCreated = marqueRepository.save(marqueRequest);
        return new ResultDto(true,"Marque successfully created !" ,marqueCreated,HttpStatus.CREATED.value(), null);
    }
    
    public ResultDto updateMarque(long id, MarqueDto marqueRequest){
        
        ResultDto resultDto;
        Marque marque = marqueRepository.findById(id).orElse(null);
        if(marque==null) resultDto = new ResultDto(false, "Failed to update : marque to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            marque.setName(marqueRequest.getName());
            Marque marqueUpdated = marqueRepository.saveAndFlush(marque);
            resultDto = new ResultDto(true, "Marque updated successfully !", marqueUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }
    
    public ResultDto findMarque(Long id){
        
        Marque marque = marqueRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Marque found !" , marque ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Marque not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return marque == null ? failed : sucess;
    }
    
    public ResultDto findMarque(String name){
        
        Marque marque = marqueRepository.findByName(name).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Annonceur found !" , marque ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Annonceur not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return marque == null ? failed : sucess;
    }
    
    public ResultDto findAllMarque() {
        
        List<Marque> marqueFoundList = marqueRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of marque !" , marqueFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return marqueFoundList.isEmpty() ? failed : sucess;
    }
    
    public ResultDto deleteMarque(Long id){
        
        Marque marque = marqueRepository.findById(id).orElse(null);
        if(marque==null) return new ResultDto(false,"Deleting failed : marque not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        marqueRepository.delete(marque);
        return new ResultDto(true,"Deleted successfull" ,marque,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteMarque(String name){
        
        Marque marque = marqueRepository.findByName(name).orElse(null);
        if(marque==null) return new ResultDto(false,"Deleting failed : marque not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        marqueRepository.delete(marque);
        return new ResultDto(true,"Deleted successfull" ,marque,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteAllMarque(){
        
        List<Marque> marqueFoundList = marqueRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of marque deleted !", marqueFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no marque found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(marqueFoundList.isEmpty()) result = failed;
        else {
            marqueRepository.deleteAll();
            result = success;
        }
        return result;
    }
    
}

