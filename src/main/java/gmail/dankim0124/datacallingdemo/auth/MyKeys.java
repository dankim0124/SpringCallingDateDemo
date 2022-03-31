package gmail.dankim0124.datacallingdemo.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MyKeys {
    @Value("${security.access-key}")
    private String accessKey;

    @Value("${security.secret-key}")
    private String secretKey;
}
