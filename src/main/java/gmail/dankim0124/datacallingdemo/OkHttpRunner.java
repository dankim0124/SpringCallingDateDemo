package gmail.dankim0124.datacallingdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gmail.dankim0124.datacallingdemo.account.api.client.AccountClient;
import gmail.dankim0124.datacallingdemo.model.TickRes;
import gmail.dankim0124.datacallingdemo.model.concurrency.ConcurrentVariable;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class OkHttpRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(OkHttpRunner.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    OkHttpClient basicOkHttpClient;

    @Autowired
    AccountClient accountClient;

    private ConcurrentVariable<Long, TickRes> BTCMapCustom = new ConcurrentVariable<Long, TickRes>(10000, 0.75F, 100);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 100; i++) {
            //req api
            Response response = accountClient.getAccounts();

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
            System.out.println(BTCMapCustom.size());
            Thread.sleep(1000);
        }

        //System.out.println(BTCMapCustom.getListOfValue());

    }
}
