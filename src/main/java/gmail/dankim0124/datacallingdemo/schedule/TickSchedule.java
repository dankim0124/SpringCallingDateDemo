package gmail.dankim0124.datacallingdemo.schedule;

import gmail.dankim0124.datacallingdemo.model.RestTick;
import gmail.dankim0124.datacallingdemo.model.TicksRecord;
import gmail.dankim0124.datacallingdemo.model.concurrency.ConcurrentVariable;
import gmail.dankim0124.datacallingdemo.repository.TickResRepository;
import gmail.dankim0124.datacallingdemo.tickService.TickService;
import okhttp3.Call;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@EnableAsync
@Component
public class TickSchedule {

    Logger logger = LoggerFactory.getLogger(TickSchedule.class);

    private HashMap<String, TicksRecord> ticksRecordMap;

    private final String[] conins = {"KRW-BTC","KRW-ETH","BTC-ETH","BTC-LTC","BTC-XRP","BTC-ETC","BTC-OMG","KRW-XRP","BTC-CVC","BTC-DGB","BTC-SC","BTC-SNT","BTC-WAVES","BTC-NMR","BTC-XEM"};

    @Autowired
    TickResRepository tickResRepository;

    @Autowired
    private TickService tickService;


    public TickSchedule(){
        ticksRecordMap = new HashMap<>();
        for(String coin: conins){
            TicksRecord ticksRecord = new TicksRecord(coin);
            ticksRecordMap.put(coin,ticksRecord);
        }
    }

    @Scheduled(fixedRate = 2000)
    public void fixedDelaytickBTC() throws UnsupportedEncodingException, NoSuchAlgorithmException, InterruptedException {
        for (String coin : conins) {
            Request req = tickService.currentTickReq("market=" + coin +"&count=200");
            Call call = tickService.makeCall(req);
            tickService.publichTickCallback(call, ticksRecordMap.get(coin).getConcurrentVariable());
            Thread.sleep(100);// 이거  수정의 필요가 있음
        }
    }

    @Scheduled(fixedRate = 10000, initialDelay = 3000)
    public synchronized void fixedDelayConsumetickBTC() {

        for(String coin : conins) {
            ConcurrentVariable concurrentVariable = ticksRecordMap.get(coin).getConcurrentVariable();
            Long lastConsumedTimeStamp = ticksRecordMap.get(coin).getLastTimeStamp();
            ArrayList<RestTick> newTicks = new ArrayList<>();

            List<RestTick> ticksFromUpbit = concurrentVariable.atomicValueListAndClear();

            // 마지막 타임 스탬프 도출
            for (RestTick tick : ticksFromUpbit) {
                if (tick.getTimestamp() >= lastConsumedTimeStamp) {
                    newTicks.add(tick);
                }
            }

            // lastTimeStamp 재조정.
            for (RestTick tick : newTicks) {
                lastConsumedTimeStamp = Math.max(tick.getTimestamp(), lastConsumedTimeStamp);
            }

            ticksRecordMap.get(coin).setLastTimeStamp(lastConsumedTimeStamp);

            logger.info("----------------------coin : {} ------------------", coin);
            logger.info("SYSTEM MILLIS: {}", System.currentTimeMillis());
            logger.info("SIZE OF NEW TICKS : {}", newTicks.size());
            logger.info("LAST TIME STAMP : {}", lastConsumedTimeStamp);


            int size= tickResRepository.saveAll(newTicks).size();
            logger.info("JPA insert = {}" , size);

            logger.info("-----------------------------------------------------------\n");

        }
    }


}
