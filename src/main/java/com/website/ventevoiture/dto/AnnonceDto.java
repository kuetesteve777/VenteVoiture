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
public class AnnonceDto {
    
    private String description; 
    private String voitureName;
    private String annonceurEmail; //
}
