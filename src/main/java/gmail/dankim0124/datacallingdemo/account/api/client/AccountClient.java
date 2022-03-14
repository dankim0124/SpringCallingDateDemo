package gmail.dankim0124.datacallingdemo.account.api.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gmail.dankim0124.datacallingdemo.jwt.JwtTokenProvider;
import gmail.dankim0124.datacallingdemo.common.UpbitApiPath;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AccountClient {

    private Logger logger = LoggerFactory.getLogger(AccountClient.class);

    private final JwtTokenProvider jwtTokenProvider;

    private final OkHttpClient basicOkHttpClient;

    public Response getAccounts() throws IOException, NoSuchAlgorithmException {

        String query_string = "market=KRW-BTC&count=100";

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/trades/ticks"+ "?" + query_string)
                .get()
                .addHeader("Accept", "application/json")
                //.addHeader("Authorization", jwtTokenProvider.getAuthorization(query_string))
                .build();

        Response response = basicOkHttpClient.newCall(request).execute();

        return basicOkHttpClient.newCall(request).execute();

        //sequential_id 필드는 체결의 유일성 판단을 위한 근거로 쓰일 수 있습니다. 하지만 체결의 순서를 보장하지는 못합니다
        //https://docs.upbit.com/reference/%EC%B5%9C%EA%B7%BC-%EC%B2%B4%EA%B2%B0-%EB%82%B4%EC%97%AD
    }


}
