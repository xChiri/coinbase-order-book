package com.george.gsrtest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeartbeatMessage extends Message {
    @JsonProperty(value = "sequence", required = true)
    private String sequence;

    @JsonProperty(value = "last_trade_id")
    private String lastTradeId;

    @JsonProperty(value = "product_id")
    private String productId;

    @JsonProperty(value = "time", required = true)
    private LocalDateTime time;

}
