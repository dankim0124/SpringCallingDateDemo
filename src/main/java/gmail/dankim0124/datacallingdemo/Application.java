package gmail.dankim0124.datacallingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}



// TODO
// 1) @schedule 함수들 코드로 생성 가능하게 변경
// 2) jpa 설정
// 3) syconronized 를 저기 스캐쥴 말고 자료형에서 처리하게 하기
//

