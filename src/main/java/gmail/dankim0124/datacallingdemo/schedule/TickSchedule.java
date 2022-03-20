package gmail.dankim0124.datacallingdemo.schedule;

import gmail.dankim0124.datacallingdemo.model.TickRes;
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
import java.text.SimpleDateFormat;
import java.util.*;

@EnableAsync
@Component
public class TickSchedule {
    Logger logger = LoggerFactory.getLogger(TickSchedule.class);

    @Autowired
    TickResRepository tickResRepository;

    @Autowired
    private TickService tickService;

    // TO DO
    // : 마지막 시간 기준으로 카운트 안하기 .

    private Long lastConsumedTimeStamp = 0L;
    ConcurrentVariable<Long, TickRes> concurrentVariable = new ConcurrentVariable<>();

    @Scheduled(fixedRate = 1000)
    public void fixedDelaytickBTC() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Request req = tickService.currentTickReq("market=KRW-BTC&count=100");
        Call call = tickService.makeCall(req);
        tickService.publichTickCallback(call, concurrentVariable);
    }

    @Scheduled(fixedRate = 10000, initialDelay = 3000)
    public synchronized void fixedDelayConsumetickBTC() {

        List<TickRes> ticksFromUpbit = concurrentVariable.atomicValueListAndClear();


        logger.info("last consumed Time stamp : {}", lastConsumedTimeStamp);

        // 마지막 타임 스탬프 도출
        ArrayList<TickRes> newTicks = new ArrayList<>();
        for (TickRes tick : ticksFromUpbit) {
            if(tick.getTimestamp() >= lastConsumedTimeStamp){
                newTicks.add(tick);
            }
        }

        // lastTimeStamp 재조정.
        for(TickRes tick : newTicks ){
            lastConsumedTimeStamp  = Math.max(tick.getTimestamp(),lastConsumedTimeStamp);
        }

        logger.info("------------------req.millis : {} ------------------",System.currentTimeMillis());
        logger.info("SIZE OF NEW TICKS : {}" , newTicks.size());
        logger.info("LAST TIME STAMP : {}" ,lastConsumedTimeStamp);
        logger.info("-----------------------------------------------------------\n");

        tickResRepository.saveAll(newTicks);

    }


}
