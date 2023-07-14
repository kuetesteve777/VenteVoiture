package com.website.ventevoiture.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author kuetesteve21
 */

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Configuration
@Entity
@Table(name="annonce")
public class Annonce implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Temporal(TemporalType.DATE)
    @Column
    private Date dateAjout;
    
    @Temporal(TemporalType.DATE)
    @Column
    private Date dateModif;
    
    @Column
    private String description; 
    
    @OneToOne
    @JoinColumn(name="voiture_id")
    private Voiture voiture;
    
    //@NotNull(message="The field annonceur  is required by me !")
    @JsonBackReference(value = "annonces-annonceur")
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name="annonceur_id")
    private Annonceur annonceur;
}
