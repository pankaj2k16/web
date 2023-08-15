package com.rajlee.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;

@SpringBootApplication
public class JwtRajleeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtRajleeApplication.class, args);
        HashSet<String> hs = new HashSet<>();
        hs.add("ll");
        String l = String.valueOf(hs.size());
        System.out.printf(l);
    }

}
