package br.com.mrchagas.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GerarHashSenha {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        // senha em texto plano
        String senha = "123";

        // gera o hash
        String hash = encoder.encode(senha);

        System.out.println("Senha: " + senha);
        System.out.println("Hash: " + hash);

        // testa se confere
        boolean confere = encoder.matches(senha, hash);
        System.out.println("Confere? " + confere);
    }
}
