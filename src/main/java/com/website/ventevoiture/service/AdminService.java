package com.website.ventevoiture.service;


import com.website.ventevoiture.dto.AdminDto;
import com.website.ventevoiture.dto.LoginDto;
import com.website.ventevoiture.dto.ResultDto;
import com.website.ventevoiture.model.Administrateur;
import com.website.ventevoiture.repository.AdminRepository;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 *
 * @author kuetesteve21
 */
@Data
@Component
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    //@Autowired
    private BindingResult bindingResult;

    @Autowired
    private Validator validator;
    
    private MethodArgumentNotValidException m;
    
    public ResultDto createAdmin(@Valid  AdminDto adminDto) {
        
        Administrateur adminRequest = modelMapper.map(adminDto, Administrateur.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<Administrateur>> violations = validator.validate(adminRequest);

        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Administrateur> violation : violations) {
                errors.add(violation.getMessage());
            }
            return new ResultDto(false,"Failed to create admin !" ,null,HttpStatus.BAD_REQUEST.value(), errors);
        }
        
        if (bindingResult.hasErrors()) {
            return new ResultDto(false,"Failed to create admin !" ,bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST.value(), null);
        }
     
        Administrateur adminCreated = adminRepository.save(adminRequest);
        return new ResultDto(true,"Admin successfully created !" ,adminCreated,HttpStatus.CREATED.value(), null);
    }
    
    public ResultDto updateAdmin(long id, AdminDto adminRequest){
        
        ResultDto resultDto;
        Administrateur admin = adminRepository.findById(id).orElse(null);
        if(admin==null) resultDto = new ResultDto(false, "Failed to update : admin to update is not found !", null, HttpStatus.NOT_FOUND.value(), null);
        else {
            admin.setName(adminRequest.getName());
            admin.setUserName(admin.getUserName());
            admin.setEmail(adminRequest.getEmail());
            admin.setPassword(adminRequest.getPassword());
            Administrateur adminUpdated = adminRepository.saveAndFlush(admin);
            resultDto = new ResultDto(true, "Admin updated successfully !", adminUpdated, HttpStatus.OK.value(), null);
        }
        return resultDto;
    }

    public ResultDto connectAdmin(LoginDto loginRequest)  {
        
        Administrateur email = adminRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        Administrateur password = adminRepository.findByPassword(loginRequest.getPassword()).orElse(null);
        if(email==null)
            return new ResultDto(false,"Incorrect email" ,email,HttpStatus.NOT_ACCEPTABLE.value(), null);
        else if(password==null)
            return new ResultDto(false,"Incorrect password" ,password,HttpStatus.NOT_ACCEPTABLE.value(), null);      
        else{
            email.setConnected(true);
            return new ResultDto(true,"Administrator logged in successfully !" , email, HttpStatus.ACCEPTED.value(), null);
        }
    }
    
    public ResultDto findAdmin(Long id){
        
        Administrateur admin = adminRepository.findById(id).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Admin found !" , admin ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Admin not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return admin == null ? failed : sucess;
    }
    
    public ResultDto findAdmin(String name){
        
        Administrateur admin = adminRepository.findByName(name).orElse(null); 
        ResultDto sucess = new ResultDto(true,"Admin found !" , admin ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"Admin not found !" , null ,HttpStatus.NOT_FOUND.value(), null);
        return admin == null ? failed : sucess;
    }
    
    public ResultDto findAllAdmin() {
        
        List<Administrateur> adminFoundList = adminRepository.findAll();
        ResultDto sucess = new ResultDto(true,"List of admins !" , adminFoundList ,HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(true,"No content !" ,null,HttpStatus.NO_CONTENT.value(), null);        
        return adminFoundList.isEmpty() ? failed : sucess;
    }
    
    public ResultDto deleteAdmin(Long id){
        
        Administrateur admin = adminRepository.findById(id).orElse(null);
        if(admin==null) return new ResultDto(false,"Deleting failed : admin not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        adminRepository.delete(admin);
        return new ResultDto(true,"Deleted successfull" ,admin,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteAdmin(String name){
        
        Administrateur admin = adminRepository.findByName(name).orElse(null);
        if(admin==null) return new ResultDto(false,"Deleting failed : admin not found" ,null,HttpStatus.NOT_FOUND.value(), null);
        adminRepository.delete(admin);
        return new ResultDto(true,"Deleted successfull" ,admin,HttpStatus.OK.value(), null);
    }
    
    public ResultDto deleteAllAdmin(){
        
        List<Administrateur> adminFoundList = adminRepository.findAll();
        ResultDto result;
        ResultDto success = new ResultDto(true, "List of admin deleted !", adminFoundList, HttpStatus.OK.value(), null);
        ResultDto failed = new ResultDto(false, "Delete Failed : no admin found to delete!", null, HttpStatus.NO_CONTENT.value(), null);
        if(adminFoundList.isEmpty()) result = failed;
        else {
            adminRepository.deleteAll();
            result = success;
        }
        return result;
    }
}
