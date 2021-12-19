# poloniex-api-java
Java API client for the Poloniex exchange with focus on simplicity and usability. 

### Maven configuration

PoloniexClient is available on [Maven Central](http://search.maven.org/#search). You just have to add the following dependency in your `pom.xml` file.

```xml
<dependency>
  <groupId>com.github.thecookielab</groupId>
  <artifactId>PoloniexClient</artifactId>
  <version>1.1.1</version>
</dependency>
```

For ***snapshots***, add the following repository to your `pom.xml` file.

```xml
<repository>
    <id>sonatype snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```
The current snapshot version is `1.1.2-SNAPSHOT` from the [develop](https://github.com/TheCookieLab/poloniex-api-java/tree/develop) branch.
```xml
<dependency>
  <groupId>com.github.thecookielab</groupId>
  <artifactId>PoloniexClient</artifactId>
  <version>1.1.2-SNAPSHOT</version>
</dependency>
```

### User's Guide

Using this client is as simple as instantiating a new PoloniexExchangeService with your Poloniex API Key and API Secret as constructor parameters:

```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
```

Passing in null (or invalid values) for either the API Key or API Secret will prevent you from successfully calling the Poloniex Trading API methods (trading, retrieving private info related to your account, etc), but public API methods will still work. 

The PoloniexExchangeService offers the following functionality:

## Public API Methods (does not require Poloniex account)  

### Return ticker  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
PoloniexTicker btcTicker = service.returnTicker("USDT_BTC");
```

### Return all market pairs
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
List<String> marketsList = service.returnAllMarkets();
```

### Return chart data  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
List<PoloniexChartData> btcDailyChartDataStartingFromYesterday = 
  service.returnChartData("USDT_BTC", 
                        86400L, 
                        ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toEpochSecond());
```

## Trading API Methods (Requires Poloniex account and API access enabled)  

### Return complete balances
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
PoloniexCompleteBalance balances = service.returnBalances(includeZeroBalances = true);
```

### Return non-zero balances
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
PoloniexCompleteBalance balances = service.returnBalances();
```

### Return balance filtered by currency
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
PoloniexCompleteBalance btcBalance = service.returnBalance("BTC");
```

### Return fee info/schedule
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
PoloniexFeeInfo feeInfo = service.returnFeeInfo();
```

### Return open orders
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
List<PoloniexOpenOrder> UsdtBtcOpenOrders = service.returnOpenOrders("USDT_BTC");
```

### Return trade history
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
List<PoloniexTradeHistory> UsdtBtcTradeHistory = service.returnTradeHistory("USDT_BTC");
```

### Return order trades
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);
String orderNumber = "12345678";
List<PoloniexOrderTrade> orderTrades = service.returnOrderTrades(orderNumber);
```

### Buy
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);

String currencyPair = "USDT_BTC";
BigDecimal buyPrice = BigDecimal.valueOf("1980");
BigDecimal amount = BigDecimal.ONE;
boolean fillOrKill = false;
boolean immediateOrCancel = false;
boolean postOnly = false;
PoloniexOrderResult buyOrderResult = 
      service.buy(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
```

### Sell
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);

String currencyPair = "USDT_BTC";
BigDecimal sellPrice = BigDecimal.valueOf("1980");
BigDecimal amount = BigDecimal.ONE;
boolean fillOrKill = false;
boolean immediateOrCancel = false;
boolean postOnly = false;
PoloniexOrderResult buyOrderResult = 
      service.sell(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
```

### Cancel order  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);

String orderNumber = "123456789";
boolean success = service.cancelOrder(orderNumber);
```

### Move order  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);

String orderNumber = "123456789";
boolean success = service.moveOrder(orderNumber);
```

## Lending APIs
### Active loans  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);
PoloniexActiveLoanTypes activeLoans = service.returnActiveLoans();

```
### Create a loan offer  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

BigDecimal amount = new BigDecimal("0.01"); 
BigDecimal lendingRate = new BigDecimal("0.0042"); // 0.42%
int duration = 3; // 2 ~ 60
PoloniexLendingResult result = service.createLoanOffer("BTC", amount, lendingRate, duration, true);
```
### Return open loan offers  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);
List<PoloniexLoanOffer> list = service.returnOpenLoanOffers("BTC");
```

### Create loan offer  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

BigDecimal amount = new BigDecimal("0.01"); 
BigDecimal lendingRate = new BigDecimal("0.0042"); // 0.42%
int duration = 3; // 2 ~ 60
PoloniexLendingResult result = service.createLoanOffer("BTC", amount, lendingRate, duration, true);
```
### Cancel loan offer  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

PoloniexLendingResult result = service.cancelLoanOffer("49375906");
```

### Toggle auto renew  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

PoloniexLendingResult result = service.toggleAutoRenew("8654331");
```
### Lending Histories  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

// Weekly Report
List<PoloniexLendingHistory> lendingHistories = service.returnLendingHistory(24 * 7, 5000);
BigDecimal sum = BigDecimal.ZERO;
for (PoloniexLendingHistory h : lendingHistories)
{
  BigDecimal earned = h.earned;
  sum = sum.add(earned);
}
```



## Websocket Interface

Using Poloniex's websocket interface is also very simple. 
The following example sets up subscriptions to the general ticker as well as the USDT_ETH order book / trades, and then runs for 60 seconds:

```java
try (WSSClient wssClient = new WSSClient(ENDPOINT_URL)) {
            wssClient.addSubscription(PoloniexWSSSubscription.USDT_ETH, new LoggerMessageHandler());
            wssClient.addSubscription(PoloniexWSSSubscription.TICKER, new TickerMessageHandler());
            wssClient.run(60000);
}
```

To setup your own subscription handler, just implement the IMessageHandler interface and do your logic within the `public void handle(String message)` method. 


## Donations

Your support is always welcome!

BTC: 1FnpWiJ2Lo89E4x26w5jsYhmXJS9sUBR3b

ETH: 0x5F99D8DD2d504369657f15101e9a0cdF0fAbb799

