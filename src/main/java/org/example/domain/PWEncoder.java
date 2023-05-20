package org.example.domain;

import org.springframework.stereotype.Component;

@Component
public class PWEncoder {


    public String encode(String password){
        int len=password.length();

        StringBuilder sb=new StringBuilder();
        for(int i=password.length()-1;i>=0;i--){
            sb.append(password.charAt(i)+i);
        }

        return sb.toString();

    }

    public boolean matches(String password,String encoded_password){
        if(encode(password).equals(encoded_password)) return true;
        else return false;
    }

}
