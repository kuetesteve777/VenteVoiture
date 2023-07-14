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
public class AdminDto {
    
    private String name;
    private String userName; // NickName
    private String email;
    private String password;
}
