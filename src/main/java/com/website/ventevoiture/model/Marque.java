package com.website.ventevoiture.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name="marque")
public class Marque implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String name;
    
   
    @OneToMany(mappedBy="marque", cascade=CascadeType.ALL)
    @JsonManagedReference(value = "marque-voitures")  
    private List<Voiture> voitures = new ArrayList<>();
    
    @ManyToMany(mappedBy="marques", cascade=CascadeType.ALL)
    private List<Modele> modeles = new ArrayList<>();
}
