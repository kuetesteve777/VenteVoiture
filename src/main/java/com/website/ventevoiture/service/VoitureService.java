package com.website.ventevoiture.service;

import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.dto.VoitureDto;
import com.website.ventevoiture.model.Marque;
import com.website.ventevoiture.model.Voiture;
import com.website.ventevoiture.repository.AnnonceRepository;
import com.website.ventevoiture.repository.MarqueRepository;
import com.website.ventevoiture.repository.VoitureRepository;
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
public class VoitureService {
    
    @Autowired
    private VoitureRepository voitureRepository;
    
    @Autowired 
    private MarqueRepository marqueRepository;
    
    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;
    
    private MethodArgumentNotValidException m;
    
    @Transactional
    public ResultDto createVoiture(@Valid  VoitureDto voitureDto) {
        
        Voiture voitureRequest = modelMapper.map(voitureDto, Voiture.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<Voiture>> violations = validator.validate(voitureRequest);

        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Voiture> violation : violations) {
                errors.add(violation.getMessage());
            }
            return new ResultDto(false,"Failed to create voiture !" ,null,HttpStatus.BAD_REQUEST.value(), errors);
        }
        
        Marque marque = marqueRepository.findByName(voitureDto.getMarque().getName()).orElse(null);
        if(marque==null) return new ResultDto(false,"Failed to create voiture : Specified marque doesn't exit !" ,null,HttpStatus.BAD_REQUEST.value(), null);
        else voitureRequest.setMarque(marque);
        
//        Annonce annonce = annonceRepository.findById(voitureDto.getAnnonce()).orElse(null);
//        if(annonce==null) return new ResultDto(false,"Failed to create voiture : Specified annonce doesn't exit !" ,null,HttpStatus.BAD_REQUEST.value(), null);
//        else voitureRequest.setAnnonce(annonce);
//        
        Voiture voitureCreated = voitureRepository.save(voitureRequest);
        return new ResultDto(true,"Voiture successfully created !" ,voitureCreated,HttpStatus.CREATED.value(), null);
    }
    
    @Transactional
    public ResultDto updateVoiture(long id, VoitureDto voitureRequest){
        
        ResultDto resultDto;
        Voiture voiture = voitureRepository.findById(id).orElse(null);
        if(voiture==null) resultDto = new ResultDto(false, "Failed to update : voiture to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            //voiture.setName(voitureRequest.getName());
            Voiture voitureUpdated = voitureRepository.saveAndFlush(voiture);
            resultDto = new ResultDto(true, "Voiture updated successfully !", voitureUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }
    
    @Transactional
    public ResultDto findVoiture(Long id){
        
        Voiture voiture = voitureRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Voiture found !" , voiture ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Voiture not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return voiture == null ? failed : sucess;
    }
    
    @Transactional
    public ResultDto findVoiture(String name){
        
        Voiture voiture = voitureRepository.findByName(name).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Annonceur found !" , voiture ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Annonceur not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return voiture == null ? failed : sucess;
    }
    
    @Transactional
    public ResultDto findAllVoiture() {
        
        List<Voiture> voitureFoundList = voitureRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of voiture !" , voitureFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return voitureFoundList.isEmpty() ? failed : sucess;
    }
    
    @Transactional
    public ResultDto deleteVoiture(Long id){
        
        Voiture voiture = voitureRepository.findById(id).orElse(null);
        if(voiture==null) return new ResultDto(false,"Deleting failed : voiture not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        voitureRepository.delete(voiture);
        return new ResultDto(true,"Deleted successfull" ,voiture,HttpStatus.OK.value(), null);
    }
    
    @Transactional
    public ResultDto deleteVoiture(String name){
        
        Voiture voiture = voitureRepository.findByName(name).orElse(null);
        if(voiture==null) return new ResultDto(false,"Deleting failed : voiture not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        voitureRepository.delete(voiture);
        return new ResultDto(true,"Deleted successfull" ,voiture,HttpStatus.OK.value(), null);
    }
    
    @Transactional
    public ResultDto deleteAllVoiture(){
        
        List<Voiture> voitureFoundList = voitureRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of voiture deleted !", voitureFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no voiture found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(voitureFoundList.isEmpty()) result = failed;
        else {
            voitureRepository.deleteAll();
            result = success;
        }
        return result;
    }
    
}
