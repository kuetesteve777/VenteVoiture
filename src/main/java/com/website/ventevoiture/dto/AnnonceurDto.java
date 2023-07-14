package com.website.ventevoiture.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnonceurDto  {
    
    private String name;
    private String userName; // nickname 
    private String email;
    private String password;
    private String phone;
    private String adress;
    
}
