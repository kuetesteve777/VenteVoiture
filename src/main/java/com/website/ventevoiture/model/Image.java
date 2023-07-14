package com.website.ventevoiture.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author kuetesteve21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Configuration
@Entity
@Table(name="image")
public class Image implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String name;
    
    @Column
    private String type; // JPEG, JPG, PNG...
    
    @Lob
    @Column(length=100000000)
    private byte[] imgData;
    
    //@NotNull(message="The field voiture is required by me !")
    @JsonBackReference(value = "voiture-images")
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name="voiture_id")
    private Voiture voiture;
}
