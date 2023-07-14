package com.website.ventevoiture.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name="voiture")
public class Voiture implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
     @Column
    private int prix;
     
    @Column
    private String name;
    
    @Column
    private String status; // status Vendue ou en vente
    
    @Column
    private String etat; //Neuf ou occasion
    
    @Column
    private String numeroSerie; 
    
    @Column
    private String couleur;
    
    @Column
    private String annee;
    
    @Column
    private String cylindree; // Nombre de cylindre
    
    @Column
    private String typeCarburant;
    
    @Column
    private String Kilometrage; 
    

    
    @Column
    private String description; /**  Le nombre de porte, les options de la voiture(air conditionné, camera de recul, etc) 
     * l'état des lieux dressé par le controle technique, présence ou pas du carnet d'entretient, état général
     * Les accessoires qui font partie de la voiture(pneus, hiver, galerie de toit, la transmission, la consommation d'éssence, etc)*/
    
    //@NotNull(message="The marque of car is required !")
    @JsonBackReference(value = "marque-voitures")
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name="marque_id")
    private Marque marque;
    
    //@NotNull(message="The image(s) of car is required !")
    @JsonManagedReference(value = "voiture-images")
    @OneToMany(mappedBy="voiture", cascade=CascadeType.ALL)
    private List<Image> images; 
    
    //@NotNull(message="The annonce of car is required !")
    @OneToOne(mappedBy="voiture")
    private Annonce annonce;
}
