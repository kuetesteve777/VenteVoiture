package com.website.ventevoiture.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author kuetesteve21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Configuration
@Entity
@Table(name="annonceur")
public class Annonceur implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank(message = "The field name is required !")
    @Column(nullable = false)
    private String name;
    
       
    @NotBlank(message = "The field username is required !")
    @Column(nullable = false)
    private String userName;
    
    @Basic
    private boolean connected; 

    @NotBlank(message = "The field email is required !")
    @Email(message = "This email is not valid")
    @Column(length = 30, nullable = false, unique=true)
    private String email;

    @NotBlank(message = "The field password is required !")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "The field phone is required !")
    @Column(nullable = false, unique=true)
    private String phone;
    
    @NotBlank(message = "The field adress is required !")
    @Column(nullable = false)
    private String adress;
    
    @JsonManagedReference(value = "annonceur-annonces")
    @OneToMany(mappedBy="annonceur", cascade=CascadeType.ALL)
    private List<Annonce> annonces;
   
}
