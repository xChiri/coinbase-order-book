package com.george.gsrtest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Level2UpdateMessage extends Message {
    @JsonProperty(value = "product_id", required = true)
    private String productId;

    @JsonProperty(value = "changes", required = true)
    private List<List<String>> changes;

    @JsonProperty(value = "time")
    private LocalDateTime time;
}
