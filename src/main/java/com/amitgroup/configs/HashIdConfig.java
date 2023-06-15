package com.amitgroup.configs;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashIdConfig {
    @Value("${com.amit.hashids.secret_key:this is my salt}")
    String secretKey;

    @Value("${com.amit.hashids.min_length:8}")
    int minHashLength;

    @Bean
    Hashids createHashids(){
        return new Hashids(secretKey, minHashLength);
    }
}
