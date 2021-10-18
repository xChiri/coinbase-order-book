package com.george.gsrtest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Level2SnapshotMessage extends Message {
    @JsonProperty(value = "product_id", required = true)
    private String productId;

    @JsonProperty(value = "bids", required = true)
    private List<List<Double>> bids;

    @JsonProperty(value = "asks", required = false)
    private List<List<Double>> asks;
}
