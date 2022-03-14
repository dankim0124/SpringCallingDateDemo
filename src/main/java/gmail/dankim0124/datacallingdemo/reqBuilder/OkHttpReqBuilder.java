package gmail.dankim0124.datacallingdemo.reqBuilder;

import gmail.dankim0124.datacallingdemo.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Component
public class OkHttpReqBuilder {

    private Logger logger = LoggerFactory.getLogger(OkHttpReqBuilder.class);

    private final JwtTokenProvider jwtTokenProvider;

    public Request tickReq(String queryString) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return  new Request.Builder().
                url("https://api.upbit.com/v1/trades/ticks"+ "?" + queryString).
                get().
                addHeader("Accept","application/json").
                addHeader("Authorization", jwtTokenProvider.getAuthorization(queryString)).
                build();

    }

}
