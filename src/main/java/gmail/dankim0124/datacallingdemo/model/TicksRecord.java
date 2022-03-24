package gmail.dankim0124.datacallingdemo.model;

import gmail.dankim0124.datacallingdemo.model.concurrency.ConcurrentVariable;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TicksRecord {
    private long lastTimeStamp;
    private String coin;
    private ConcurrentVariable<Long,TickRes> concurrentVariable;

    public TicksRecord(String coinName){
        this.lastTimeStamp = 0;
        this.concurrentVariable = new ConcurrentVariable<>();
        this.coin = coinName;
    }
}
