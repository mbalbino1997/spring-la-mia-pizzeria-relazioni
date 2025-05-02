package org.lessons.java.spring_la_mia_pizzeria_crud.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptPasswordGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("user123");
        String bcryptPasswordWithPrefix = "{bcrypt}" + hashedPassword;
        System.out.println("La password criptata con prefisso è: " + bcryptPasswordWithPrefix);
    }
}
