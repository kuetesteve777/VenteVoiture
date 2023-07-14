package com.website.ventevoiture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author kuetesteve21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoitureDto {
    
    private int prix;
    
    private String name;

    private String status; // status Vendue ou en vente

    private String numeroSerie;

    private String cylindree; // Nombre de cylindre

    private String typeCarburant;

    private String Kilometrage; 

    private String etat; //Neuf ou occasion

    private String description; /**  Le nombre de porte, les options de la voiture(air conditionné, camera de recul, etc) 
     * l'état des lieux dressé par le controle technique, présence ou pas du carnet d'entretient, état général
     * Les accessoires qui font partie de la voiture(pneus, hiver, galerie de toit, la transmission, la consommation d'éssence, etc)*/

    private String couleur;

    private String annee;

    private MarqueDto marque;

    //private List<Image> images; 
    
    //private long annonce;
}
