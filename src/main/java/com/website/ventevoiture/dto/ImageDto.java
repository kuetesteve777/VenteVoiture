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
public class ImageDto {
 
    private String name;
    private String type; // JPEG, JPG, PNG...
    private byte[] data;
    
}
