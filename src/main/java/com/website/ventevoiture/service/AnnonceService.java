package com.website.ventevoiture.service;

import com.website.ventevoiture.dto.AnnonceDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.model.Annonce;
import com.website.ventevoiture.model.Annonceur;
import com.website.ventevoiture.model.Voiture;
import com.website.ventevoiture.repository.AnnonceRepository;
import com.website.ventevoiture.repository.AnnonceurRepository;
import com.website.ventevoiture.repository.VoitureRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Date;
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
public class AnnonceService {
    
    @Autowired
    private AnnonceRepository annonceRepository;
    
    @Autowired
    private AnnonceurRepository annonceurRepository;
    
    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;
    
    private MethodArgumentNotValidException m;
    
    @Transactional
    public ResultDto createAnnonce(@Valid  AnnonceDto annonceDto) {
        
        Annonce annonceRequest = modelMapper.map(annonceDto, Annonce.class);
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<Annonce>> violations = validator.validate(annonceRequest);
        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Annonce> violation : violations) {
                errors.add(violation.getMessage());
            }
            return new ResultDto(false,"Failed to create annonce !" ,null,HttpStatus.BAD_REQUEST.value(), errors);
        }
        
        synchronized(this){ annonceRequest.setDateAjout(new Date());}
        
        Annonceur annonceur = annonceurRepository.findByEmail(annonceDto.getAnnonceurEmail()).orElse(null);
        if(annonceur==null) return new ResultDto(false,"Failed to create annonce : annonceur doesn't exit !" ,null,HttpStatus.BAD_REQUEST.value(), null);
        else annonceRequest.setAnnonceur(annonceur);
        
        Voiture voiture = voitureRepository.findByName(annonceDto.getVoitureName()).orElse(null);
        if(voiture==null) return new ResultDto(false,"Failed to create annonce : voiture doesn't exit !" ,null,HttpStatus.BAD_REQUEST.value(), null);
        else annonceRequest.setVoiture(voiture);
        
        Annonce annonceCreated = annonceRepository.save(annonceRequest);
        return new ResultDto(true,"Annonce successfully created !" ,annonceCreated,HttpStatus.CREATED.value(), null);
    }
    
    public ResultDto updateAnnonce(long id, AnnonceDto annonceRequest){
        
        ResultDto resultDto;
        Annonce annonce = annonceRepository.findById(id).orElse(null);
        if(annonce==null) resultDto = new ResultDto(false, "Failed to update : annonce to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            annonce.setDateModif(new Date());
            annonce.setDescription(annonceRequest.getDescription());
            //annonce.setVoiture(annonceRequest.getVoiture());
            Annonce annonceUpdated = annonceRepository.saveAndFlush(annonce);
            resultDto = new ResultDto(true, "annonce updated successfully !", annonceUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }

    
    public ResultDto findAnnonce(Long id){
        
        Annonce annonce = annonceRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"annonce found !" , annonce ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"annonce not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return annonce == null ? failed : sucess;
    }
    
    public ResultDto findAllAnnonce() {
        
        List<Annonce> userFoundList = annonceRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of annonces !" , userFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return userFoundList.isEmpty() ? failed : sucess;
    }
    
    public ResultDto deleteAnnonce(Long id){
        
        Annonce annonce = annonceRepository.findById(id).orElse(null);
        if(annonce==null) return new ResultDto(false,"Deleting failed : annonce not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        annonceRepository.delete(annonce);
        return new ResultDto(true,"Deleted successfull" ,annonce,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteAllAnnonce(){
        
        List<Annonce> userFoundList = annonceRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of annonce deleted !", userFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no annonce found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(userFoundList.isEmpty()) result = failed;
        else {
            annonceRepository.deleteAll();
            result = success;
        }
        return result;
    }
}