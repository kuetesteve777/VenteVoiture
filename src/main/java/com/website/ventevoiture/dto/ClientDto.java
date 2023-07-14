package com.website.ventevoiture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto  {
    
    private String name;
    private String userName; // NickName
    private String email;
    private String password;
}
