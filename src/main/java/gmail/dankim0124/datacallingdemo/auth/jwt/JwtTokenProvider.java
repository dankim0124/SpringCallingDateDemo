package gmail.dankim0124.datacallingdemo.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gmail.dankim0124.datacallingdemo.auth.MyKeys;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class JwtTokenProvider {


    private final MyKeys keys;

    public JwtTokenProvider(MyKeys keys) {
        this.keys = keys;
    }

    public String createToken() {
        final Algorithm algorithm = Algorithm.HMAC256(keys.getSecretKey());
        return JWT.create().
                withClaim("access-key",keys.getAccessKey() ).
                withClaim("nonce", UUID.randomUUID().toString()).
                sign(algorithm);
    }

    public String createToken(String queryString) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        final Algorithm algorithm = Algorithm.HMAC256(keys.getSecretKey());

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("utf8"));

        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

        return JWT.create().
                withClaim("access-key",keys.getAccessKey() ).
                withClaim("nonce", UUID.randomUUID().toString()).
                withClaim("query_hash",queryHash).
                withClaim("query_hash_alg","SHA512").
                sign(algorithm);
    }

    public String getAuthorization(String queryString) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return "Bearer " + this.createToken(queryString);
    }





}
