### Coinbase bid/ask 10-level order book (GSR test)

Command-line application printing the 10-level bid/ask order books for an instrument pair specified as a single argument. Can be stopped by `Ctrl^C`.

How to run: `./mvnw spring-boot:run -Dspring-boot.run.arguments="ETH-BTC"`

Output sample:

```2021-10-18 01:54:37.734  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : Printing bids/asks order books for tick @ 2021-10-18T00:54:37.682848
2021-10-18 01:54:37.735  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : Bids order book
2021-10-18 01:54:37.735  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06211 6.38940175
2021-10-18 01:54:37.735  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.0621 2.81326917
2021-10-18 01:54:37.735  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06209 8.23917098
2021-10-18 01:54:37.735  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06208 9.6593704
2021-10-18 01:54:37.735  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06207 24.39872723
2021-10-18 01:54:37.736  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06206 18.66911889
2021-10-18 01:54:37.736  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06205 0.16116036
2021-10-18 01:54:37.736  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06204 1.78909732
2021-10-18 01:54:37.736  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06203 9.74493
2021-10-18 01:54:37.737  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : BUY 0.06201 0.51191303
2021-10-18 01:54:37.737  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : Asks order book
2021-10-18 01:54:37.737  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06213 0.825
2021-10-18 01:54:37.738  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06214 6.98598745
2021-10-18 01:54:37.738  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06215 14.83301979
2021-10-18 01:54:37.738  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06216 16.43589333
2021-10-18 01:54:37.738  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06217 13.40728848
2021-10-18 01:54:37.738  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06218 4.67468437
2021-10-18 01:54:37.739  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06219 17.91612769
2021-10-18 01:54:37.739  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.0622 7.6766
2021-10-18 01:54:37.739  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06221 4.55
2021-10-18 01:54:37.739  INFO 3996 --- [ient-SecureIO-2] com.george.gsrtest.core.WsClient         : SELL 0.06223 44.61742544
```
