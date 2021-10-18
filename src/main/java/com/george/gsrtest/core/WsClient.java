package com.george.gsrtest.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.george.gsrtest.models.*;
import com.george.gsrtest.models.enums.OrderType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WsClient extends TextWebSocketHandler implements CommandLineRunner {
    private static final int MAX_MESSAGE_SIZE = 1000000;
    private static final int ORDER_BOOK_SIZE = 10;

    private final WebSocketSession session;
    private final String coinbaseWs;
    private final String subscribeTemplate;
    private final ApplicationContext applicationContext;

    private List<Order> bidsOrderBook;
    private List<Order> askOrderBook;
    private final ObjectMapper objectMapper;

    public WsClient(@Value("${coinbase.ws-feed}") String coinbaseFeed,
                    @Value("classpath:data/level2_subscribe_template.json") Resource templateResource,
                    ApplicationContext applicationContext, ObjectMapper objectMapper) throws ExecutionException, InterruptedException, IOException {
        this.applicationContext = applicationContext;
        this.coinbaseWs = coinbaseFeed;
        this.objectMapper = objectMapper;

        WebSocketClient client = new StandardWebSocketClient();
        this.session = client.doHandshake(this, new WebSocketHttpHeaders(), URI.create(coinbaseFeed)).get();

        this.session.setTextMessageSizeLimit(MAX_MESSAGE_SIZE);
        this.subscribeTemplate = StreamUtils.copyToString(templateResource.getInputStream(), StandardCharsets.UTF_8);

        this.bidsOrderBook = new ArrayList<>();
        this.askOrderBook = new ArrayList<>();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageStr = message.getPayload().toString();
        Message rootMessage = objectMapper.readValue(messageStr, Message.class);
        switch (rootMessage.getType()) {
            case "subscriptions":
                SubscriptionMessage subscriptionMessage = objectMapper.readValue(messageStr, SubscriptionMessage.class);
                log.info("Subscribed successfully to channels: {}", subscriptionMessage.getChannels());
                return;
            case "heartbeat":
                HeartbeatMessage heartbeatMessage = objectMapper.readValue(messageStr, HeartbeatMessage.class);
                System.out.println();
                log.info("Printing bids/asks order books for tick @ {}", heartbeatMessage.getTime());
                printOrderBook("Bids", bidsOrderBook);
                printOrderBook("Asks", askOrderBook);
                return;
            case "snapshot":
                Level2SnapshotMessage snapshotMessage = objectMapper.readValue(messageStr, Level2SnapshotMessage.class);
//                log.info(snapshotMessage.toString());
                bidsOrderBook = snapshotMessage.getBids().stream()
                        .limit(ORDER_BOOK_SIZE)
                        .map(bidPricePair -> Order.builder().orderType(OrderType.BUY).price(bidPricePair.get(0)).size(bidPricePair.get(1)).build())
                        .collect(Collectors.toList());
                askOrderBook = snapshotMessage.getAsks().stream()
                        .limit(ORDER_BOOK_SIZE)
                        .map(askPricePair -> Order.builder().orderType(OrderType.SELL).price(askPricePair.get(0)).size(askPricePair.get(1)).build())
                        .collect(Collectors.toList());
                return;
            case "l2update":
                Level2UpdateMessage updateMessage = objectMapper.readValue(messageStr, Level2UpdateMessage.class);
                List<Order> updates = updateMessage.getChanges().stream()
                        .map(change -> Order.builder().orderType(OrderType.getEnum(change.get(0)))
                                .price(Double.parseDouble(change.get(1)))
                                .size(Double.parseDouble(change.get(2))).build())
                        .collect(Collectors.toList());
                List<Order> bidUpdates = updates.stream().filter(order -> order.getOrderType() == OrderType.BUY).collect(Collectors.toList());
                List<Order> askUpdates = updates.stream().filter(order -> order.getOrderType() == OrderType.SELL).collect(Collectors.toList());
                // remove orders to be updated
                bidsOrderBook.removeIf(bid -> bidUpdates.stream().map(Order::getPrice).collect(Collectors.toList()).contains(bid.getPrice()));
                askOrderBook.removeIf(ask -> askUpdates.stream().map(Order::getPrice).collect(Collectors.toList()).contains(ask.getPrice()));
                // remove orders of 0 quantity
                bidUpdates.removeIf(order -> order.getSize() == 0);
                askUpdates.removeIf(order -> order.getSize() == 0);
                // update order book
                bidsOrderBook.addAll(bidUpdates);
                askOrderBook.addAll(askUpdates);
                // sort and remove orders beyond 10 levels
                bidsOrderBook.sort(Order.BID_COMPARATOR);
                askOrderBook.sort(Order.ASK_COMPARATOR);
                bidsOrderBook = bidsOrderBook.stream().limit(ORDER_BOOK_SIZE).collect(Collectors.toList());
                askOrderBook = askOrderBook.stream().limit(ORDER_BOOK_SIZE).collect(Collectors.toList());
//                log.info(updateMessage.toString());
                return;
            default:
                log.info("Unknown message type: {}", rootMessage.getType());
        }
    }

    private void printOrderBook(String orderBookType, List<Order> orderBook) {
        if (orderBook != null && orderBook.size() > 0) {
            log.info("{} order book", orderBookType);
            for (Order order: orderBook) {
                log.info("{} {} {}", order.getOrderType(), order.getPrice(), order.getSize());
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if (args == null || args.length != 1) {
            log.error("Please provide exactly one argument: the instrument to build the order book for");
            SpringApplication.exit(applicationContext, () -> 0);
            return;
        }

        TextMessage message = new TextMessage(String.format(subscribeTemplate, args[0]));
        session.sendMessage(message);
    }
}
