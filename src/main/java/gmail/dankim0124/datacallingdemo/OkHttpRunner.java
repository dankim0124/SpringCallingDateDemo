package gmail.dankim0124.datacallingdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;



@Component
public class OkHttpRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(OkHttpRunner.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
    }


}
