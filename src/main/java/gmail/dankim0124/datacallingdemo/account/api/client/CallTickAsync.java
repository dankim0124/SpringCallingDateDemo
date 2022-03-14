package gmail.dankim0124.datacallingdemo.account.api.client;


import gmail.dankim0124.datacallingdemo.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Component
public class CallTickAsync {
    private Logger logger = LoggerFactory.getLogger(CallTickAsync.class);
    private final OkHttpClient basicOkHttpClient;
    private final JwtTokenProvider jwtTokenProvider;

    public void AsyncTickReq() throws NoSuchAlgorithmException, IOException {
        String query_string = "market=KRW-BTC&count=100";

        Request request = new Request.Builder()
                .url("https://api.upbit.com/v1/trades/ticks"+ "?" + query_string)
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", jwtTokenProvider.getAuthorization(query_string))
                .build();

        Call call = basicOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException {
                // ...
            }

            public void onFailure(Call call, IOException e) {
               e.printStackTrace();
            }
        });
    }
}

