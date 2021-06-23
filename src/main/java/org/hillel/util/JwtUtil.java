package org.hillel.util;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("app.jwt.secret.key:12345678")
    private String secretKey;

    public static final String BEARER = "Bearer ";

    public String getEmailFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);   // getSubject here is email
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsFunction){
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();  //parseClaimsJws makes decripting
        return claimsFunction.apply(body);
    }

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis()+(60*30*1000)))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public String getTokenFromRequest(final HttpServletRequest request){
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Strings.hasText(header) && header.startsWith(BEARER)){
            return header.substring(7);
        }
        return  null;
    }
}
