package gmail.dankim0124.datacallingdemo.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {

    private String currency;
    private String balance;
    private String locked;

    @JsonProperty("avg_buy_price")
    private String avgBuyPrice;

    @JsonProperty("avg_buy_price_modified")
    private Boolean avgBuyPriceModified;

    @JsonProperty("unit_currency")
    private String unitCurrency;
}
