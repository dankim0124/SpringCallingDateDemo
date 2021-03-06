package gmail.dankim0124.datacallingdemo.reqBuilder;

import gmail.dankim0124.datacallingdemo.common.UpbitApiPath;
import gmail.dankim0124.datacallingdemo.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Component
public class OkHttpReqs {

    private final JwtTokenProvider jwtTokenProvider;

    public Request tickReq(String queryString) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return  new Request.Builder().
                url(UpbitApiPath.BASE_SERVER_URL + UpbitApiPath.TICK_API+ "?" + queryString).
                get().
                addHeader("Accept","application/json").
                addHeader("Authorization", jwtTokenProvider.getAuthorization(queryString)).
                build();
    }

}
