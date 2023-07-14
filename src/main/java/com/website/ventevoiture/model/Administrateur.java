package com.website.ventevoiture.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
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
@Table(name="administrator")
public class Administrateur implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "The field name is required !")
    private String name;

    
    @Column(nullable = false)
    @NotBlank(message = "The field userName is required !")
    private String userName; // NickName

    @NotBlank(message = "The field email is required !")
    @Email(message = "This email is not valid")
    @Column(length = 30, nullable = false, unique=true)
    private String email;
    
    @Basic
    private boolean connected;

    @NotBlank(message = "The field password is required !")
    @Column(nullable = false)
    private String password;
    
}
