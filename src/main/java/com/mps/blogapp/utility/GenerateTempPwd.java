package com.mps.blogapp.utility;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateTempPwd {
    public String genTempPwd(){
        return UUID.randomUUID().toString().replace("-","").substring(0,8);
    }
}
