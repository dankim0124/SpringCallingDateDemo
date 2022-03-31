package gmail.dankim0124.datacallingdemo.tickService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gmail.dankim0124.datacallingdemo.model.RestTick;
import gmail.dankim0124.datacallingdemo.model.concurrency.ConcurrentVariable;
import gmail.dankim0124.datacallingdemo.reqBuilder.OkHttpReqs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TickService {
    Logger logger = LoggerFactory.getLogger(TickService.class);

    private static long ETHReqCount = 0L;
    private static long lastETHReqTime = 0L;
    private volatile static long remainedCallback = 0L;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    OkHttpClient basicOkHttpClient;

    @Autowired
    OkHttpReqs okHttpReqs;

    //register callback
    public void publichTickCallback(Call call, ConcurrentVariable concurrentVariable) {
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException {
                handleReceive(response, concurrentVariable);
            }

            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    // async callback
    public void handleReceive(Response response, ConcurrentVariable concurrentVariable) throws IOException {
        if (response.code() != 200) {
            logger.info("--------------------recieved message------------------------");
            logger.info("receivedCode : {}", response.code());
            logger.info("sent at  : {}", response.sentRequestAtMillis());
            logger.info("error: {}", response.body().string());
            logger.info("request: {}", response.request().url());
            logger.info("--------------------------------------------------------\n");
            return;
        }

        // 응답을 TickRes 자료형으로
        List<RestTick> tickResList = objectMapper.readValue(
                response.body().string(),

                new TypeReference<List<RestTick>>() {
                });

        //sequential_id 를 키로 갖는 맵으로
        Map<Long, RestTick> map = tickResList.stream().collect(
                Collectors.toMap(
                        RestTick::getSequentialId,
                        item -> item,
                        (oldKey, newKey) -> newKey
                )
        );

        // 맵을 공유변수에 저장 .
        concurrentVariable.addAll(map);
    }

    public Request currentTickReq(String queryString) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return okHttpReqs.tickReq(queryString);
    }

    public Call makeCall(Request request) {
        return basicOkHttpClient.newCall(request);
    }

}
