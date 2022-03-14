package gmail.dankim0124.datacallingdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gmail.dankim0124.datacallingdemo.account.api.client.AccountClient;
import gmail.dankim0124.datacallingdemo.model.TickRes;
import gmail.dankim0124.datacallingdemo.model.concurrency.ConcurrentVariable;
import gmail.dankim0124.datacallingdemo.reqBuilder.OkHttpReqs;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OkHttpRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(OkHttpRunner.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    OkHttpClient basicOkHttpClient;

    @Autowired
    AccountClient accountClient;

    @Autowired
    OkHttpReqs okHttpReqs;

    private ConcurrentVariable<Long, TickRes> BTCMapCustom = new ConcurrentVariable<Long, TickRes>(10000, 0.75F, 100);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 100; i++) {
            //req api
            Request BTCCurrentTickReq = okHttpReqs.tickReq("market=KRW-BTC&count=100");

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

            Thread.sleep(1000);
        }

        //System.out.println(BTCMapCustom.getListOfValue());

    }

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
        BTCMapCustom.addAll(map);
        logger.info("sent at {} | received {} ticks" , response.sentRequestAtMillis() , BTCMapCustom.size());

    }
}
