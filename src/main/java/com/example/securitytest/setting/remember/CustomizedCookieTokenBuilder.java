package com.example.securitytest.setting.remember;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class CustomizedCookieTokenBuilder extends TokenBasedRememberMeServices {

    public CustomizedCookieTokenBuilder(UserDetailsService userDetailsService) {
        super("$X@Jv)dP^grN8VLc#aS0%2=IfYo$uCA9n!Do&i*T+", userDetailsService);
    }

    /**
     * We create a key based on algorithm passed to it;
     * It means create a key for this algorithm;
     * The algorithm specify the teeth of the key passed to it;
     * The key is specified above;
     */
    @Override
    public String makeTokenSignature(long tokenExpiryTime, String username, String password,
                                     RememberMeTokenAlgorithm rememberMeTokenAlgorithm) {

        String cookieData = username + ";" + tokenExpiryTime;
        try {
            Mac hmac = Mac.getInstance("HmacSHA3-256", new BouncyCastleProvider());//HMAC: Hash_based Message Authentication Code;
            var secretKeySpec = new SecretKeySpec(getKey().getBytes(StandardCharsets.UTF_8), hmac.getAlgorithm()); //JUST Creates a key for Authentication
            hmac.init(secretKeySpec);   //Giving a key to a Mac Engine, actually it is Mac that authenticates messages NOT SecretKeySpec;
            byte[] bytes = hmac.doFinal(cookieData.getBytes(StandardCharsets.UTF_8)); //The message is passed to engine, So the key is the way to open message Lock;
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
