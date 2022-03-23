package gmail.dankim0124.datacallingdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "btc_ticks")
public class TickRes {
    @Id
    @JsonProperty("sequential_id")
    private Long sequentialId;

    @JsonProperty("market")
    private String market;
    @JsonProperty("trade_date_utc")
    private String tradeDateUtc;
    @JsonProperty("trade_time_utc")
    private String tradeTimeUtc;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("trade_price")
    private Double tradePrice;
    @JsonProperty("trade_volume")
    private Double tradeVolume;
    @JsonProperty("prev_closing_price")
    private Double prevClosingPrice;
    @JsonProperty("change_price")
    private Double changePrice;
    @JsonProperty("ask_bid")
    private String askBid;
    // 자료형 : https://docs.upbit.com/reference/%EC%B5%9C%EA%B7%BC-%EC%B2%B4%EA%B2%B0-%EB%82%B4%EC%97%AD
}
