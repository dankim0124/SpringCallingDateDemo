package gmail.dankim0124.datacallingdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import gmail.dankim0124.datacallingdemo.model.pk.SocketTickPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity(name = "socket_tick_test")
@Table(name = "socket_tick_sample")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@IdClass(SocketTickPK.class)
public class SocketTick {
    @Id
    @NotNull
    @JsonProperty("sid")
    private Long sequentialId;


    @Id
    @JsonProperty("cd")
    private String code;

    @JsonProperty("ty")
    private String type;


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
