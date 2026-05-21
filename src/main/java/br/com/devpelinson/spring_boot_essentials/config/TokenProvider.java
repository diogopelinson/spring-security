package br.com.devpelinson.spring_boot_essentials.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Value("${jwt.key}")
    private String key;

    //Gerar um token
    public String gerarToken(Authentication authentication){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return buidToken(user.getUsername());
    }

    private String buidToken(String username){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigninKey())
                .compact();
    }

    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    //Validar um token
    public Boolean isTokenValid(String token){
        try {
            getClaims(token);
            return true;
        }catch (Exception err){
            return false;
        }
    }

    //Extrair informacoes do token
    private String getUsername(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token){
        //validar assinatura do token
        //validar expiracao do token
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token) //valida token eh valido
                .getPayload();
    }
}
