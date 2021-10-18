package com.george.gsrtest.models.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OrderType {
    @JsonProperty("buy")
    BUY("buy"),
    @JsonProperty("sell")
    SELL("sell");

    private static final Map<String, OrderType> NAME_ENUM_MAP;

    static {
        Map<String, OrderType> map = new ConcurrentHashMap<>();
        for (OrderType instance: OrderType.values()) {
            map.put(instance.getValue(), instance);
        }
        NAME_ENUM_MAP = Collections.unmodifiableMap(map);
    }

    @Getter
    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public static OrderType getEnum(String name) {
        return NAME_ENUM_MAP.get(name);
    }
}
