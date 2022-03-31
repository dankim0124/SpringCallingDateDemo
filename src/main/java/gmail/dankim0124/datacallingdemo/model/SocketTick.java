package gmail.dankim0124.datacallingdemo.model.concurrency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "socket_tick")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class SocketTick {
    @Id
    @NotNull
    @JsonProperty("sid")
    private Long sequentialId;

    @JsonProperty("ty")
    private String type;

    @JsonProperty("cd")
    private String code;

    @JsonProperty("tp")
    private Double tradePrice;

    @JsonProperty("tv")
    private Double tradeVolume;

    @JsonProperty("ab")
    private String askBid;

    @JsonProperty("pcp")
    private Double prev_closing_price;

    @JsonProperty("c")
    private String change;

    @JsonProperty("cp")
    private Double change_price;

    @JsonProperty("td")
    private String tradeDateUtc;

    @JsonProperty("ttm")
    private String tradeTimeUtc;

    @JsonProperty("ttms")
    private Long tradeTimestamp;

    @JsonProperty("tms")
    private Long timestamp;

   @JsonProperty("st")
   private String streamType;


}
