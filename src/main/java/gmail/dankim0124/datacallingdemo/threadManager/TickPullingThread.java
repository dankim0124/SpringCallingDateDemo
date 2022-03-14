package gmail.dankim0124.datacallingdemo.threadManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gmail.dankim0124.datacallingdemo.OkHttpRunner;
import gmail.dankim0124.datacallingdemo.account.api.client.AccountClient;
import gmail.dankim0124.datacallingdemo.model.TickRes;
import gmail.dankim0124.datacallingdemo.model.concurrency.ConcurrentVariable;
import gmail.dankim0124.datacallingdemo.reqBuilder.OkHttpReqs;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
public class TickPullingThread extends Thread{

    Logger logger = LoggerFactory.getLogger(TickPullingThread.class);

    private final String tickUrl = "https://api.upbit.com/v1/trades/ticks";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private boolean running = true;
    private ConcurrentVariable<Long, TickRes> TicksMap;
    private String targetCoin;

    public TickPullingThread(String targetCoin,ConcurrentVariable concurrentVariable){
        this.targetCoin = targetCoin;
        this.TicksMap = concurrentVariable;
    }

    @Autowired
    OkHttpClient basicOkHttpClient;

    @Autowired
    AccountClient accountClient;

    @Autowired
    OkHttpReqs okHttpReqs;


    public void handleReceive(Response response) throws IOException {
        if(response.code()!= 200){
            logger.info("receivedCode : {}", response.code());
            logger.info("sent at  : {}", response.sentRequestAtMillis());
            return;
        }

        // 응답을 TickRes 자료형으로
        List<TickRes> tickResList = objectMapper.readValue(
                response.body().string(),
                new TypeReference<List<TickRes>>() {
                });

        //sequential_id 를 키로 갖는 맵으로
        Map<Long, TickRes> map = tickResList.stream().collect(
                Collectors.toMap(
                        TickRes::getSequentialId,
                        item -> item,
                        (oldKey, newKey) -> newKey
                )
        );

        // 맵을 공유변수에 저장 .
        TicksMap.addAll(map);
        logger.info("sent at {} | received {} ticks" , response.sentRequestAtMillis() , TicksMap.size());

    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            //req api
            Request BTCCurrentTickReq = null;

            try {
                BTCCurrentTickReq = okHttpReqs.tickReq("market=KRW-BTC&count=200");
            } catch (UnsupportedEncodingException e) {
                logger.warn(e.toString());
            } catch (NoSuchAlgorithmException e) {
                logger.warn(e.toString());
            }

            Call call = basicOkHttpClient.newCall(BTCCurrentTickReq);
            call.enqueue(new Callback() {
                public void onResponse(Call call, Response response)
                        throws IOException {
                    handleReceive(response);
                }

                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.warn(e.toString());
            }
        }

    }
}
