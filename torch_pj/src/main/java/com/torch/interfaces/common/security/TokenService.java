package com.torch.interfaces.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static com.torch.interfaces.common.security.Session.buildSession;

/**
 * Created by yuanj on 9/21/16.
 */
@Component
public class TokenService {
    final ApplicationSecuritySettings applicationSecuritySettings;

    @Autowired
    public TokenService(final ApplicationSecuritySettings applicationSecuritySettings) {
        this.applicationSecuritySettings = applicationSecuritySettings;
    }

    /**
     * 生成token
     *  
     * @param session
     * @return String     
     */
    public String generate(Session session) {

        final String tokenString = Jwts.builder()
                .setIssuedAt(new Date())
                .setClaims(session.getOtherMaps())
                .setIssuer(session.getUserIdToString())
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(applicationSecuritySettings.getToken().getSecret()))
                .compact();
        return tokenString;
    }

    /**
     * 验证token
     *  
     * @param tokenString
     * @return Optional<Session> 
     */
    public Optional<Session> verify(String tokenString) {
        try {
            if (tokenString == null || "".equals(tokenString)) {
                return Optional.empty();
            }
            Claims jwtClaims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(applicationSecuritySettings.getToken().getSecret()))
                    .parseClaimsJws(tokenString).getBody();
            return Optional.of(buildSession()
                    .withUserId(Long.valueOf(jwtClaims.getIssuer()))
                    .withUsername(jwtClaims.get("username").toString()));
        } catch (SignatureException e) {
            return Optional.empty();
        }
    }
}
