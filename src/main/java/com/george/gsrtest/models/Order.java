package com.george.gsrtest.models;

import com.george.gsrtest.models.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Order {
    private OrderType orderType;
    private double price;
    private double size;

    public static final Comparator<Order> BID_COMPARATOR = (o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice());
    public static final Comparator<Order> ASK_COMPARATOR = Comparator.comparingDouble(Order::getPrice);

}
